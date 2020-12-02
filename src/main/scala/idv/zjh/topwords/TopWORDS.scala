package idv.zjh.topwords
import org.apache.log4j.Logger
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel

/**
  * Created by qfeng on 16-7-6.
  */

/**
  * TopWORDS algorithm
  *
  * @param tauL             threshold of word length
  * @param tauF             threshold of word frequency
  * @param numIterations    number of iterations                          迭代次數
  * @param convergeTol      convergence tolerance                   1E-3  收斂判斷
  * @param textLenThld      preprocessing threshold of text length  2000  文本长度的预处理阈值
  * @param useProbThld      prune threshold of word use probability 1E-8  修剪单词使用概率的阈值
  * @param wordBoundaryThld segment threshold of word boundary score (use segment tree if set to less than 0) 分词边界得分的分割阈值
  */
class TopWORDS(private val tauL: Int,
               private val tauF: Int,
               private val numIterations: Int,
               private val convergeTol: Double,
               private val textLenThld: Int,
               private val useProbThld: Double,
               private val wordBoundaryThld: Double = 0.0
              ) extends Serializable {
  @transient private[this] val LOGGER = Logger.getLogger(this.getClass.toString)
  /**
    * Run the TopWORDS algorithm
    *
    * @param corpus          training corpus                    訓練語料庫
    * @param outputDictLoc   output dictionary location         輸出字典位置
    * @param outputCorpusLoc output segmented corpus location   输出分割语料位置
    */
  def run(corpus: RDD[String], outputDictLoc: String, outputCorpusLoc: String): Unit = {
    // preprocess the input corpus 準備輸入語料庫
    val texts = new Preprocessing(textLenThld).run(corpus).persist(StorageLevel.MEMORY_AND_DISK_SER_2)
    // generate the overcomplete dictionary 產生過於龐大的字典
    var dict = Dictionary(corpus, tauL, tauF, useProbThld)
    // initialize the loop variables 初始化迴圈變數
    var iter = 1
    var converged = false
    var lastLikelihood = -1.0
    // EM loop
    while (!converged && iter <= numIterations) {
      // update and prune the dictionary 對字典進行縮減（）
      val (updatedDict, likelihood) = updateDictionary(texts, dict)
      dict = pruneDictionary(updatedDict)
      // log info of the current iteration
      LOGGER.info("Iteration : " + iter + ", likelihood: " + likelihood + ", dictionary: " + dict.thetaS.size)
      // test the convergence condition
      //
      LOGGER.info("(likelihood - lastLikelihood)：" + (likelihood - lastLikelihood))
      LOGGER.info("math.abs((likelihood - lastLikelihood) / lastLikelihood)：" + math.abs((likelihood - lastLikelihood) / lastLikelihood))
      LOGGER.info("(convergeTol)：" + (convergeTol))


      if (lastLikelihood > 0 && math.abs((likelihood - lastLikelihood) / lastLikelihood) < convergeTol) {
        converged = true
      }
      // prepare for the next iteration
      lastLikelihood = likelihood
      iter = iter + 1
    }
    // save the result dictionary
    dict.save(outputDictLoc)
    // segment the corpus and save the segmented corpus (at most 10,000 texts per partition)
    PESegment(texts, dict).repartition(((texts.count() / 10000) + 1).toInt).saveAsTextFile(outputCorpusLoc)
    texts.unpersist()
  }

  /**
    * Update the dictionary in an iteration
    * 在迭代中更新字典
    * @param texts corpus texts
    * @param dict  dictionary
    * @return (updated dictionary, text likelihoods)
    */
  def updateDictionary(texts: RDD[String], dict: Dictionary): (Dictionary, Double) = {
    // importing spark implicits
    val spark = SparkSession.builder().getOrCreate()
    // spark 運算的真正邏輯是使用Excutor 去運算的，當有共用參數時，使用廣播變量（broadcast）
    val dictBC = spark.sparkContext.broadcast(dict)
    // calculating the likelihoods (P(T|theta)) and expectations (niS and riS)
    val dpResult = texts.map { T =>
      // 动态编程的可能性倒推
      val likelihoods = DPLikelihoodsBackward(T, dictBC.value)
      //关于预期的动态编程
      (likelihoods(0), DPExpectations(T, dictBC.value, likelihoods))
    }.persist(StorageLevel.MEMORY_AND_DISK_SER_2)
    // extract the theta values
    val expectations = dpResult.map(_._2)
    val nis = expectations.flatMap(_._1).reduceByKey(_ + _)
    val niSum = nis.map(_._2).sum()
    val thetaS = nis.map { case (word, ni) =>
      word -> ni / niSum
    }.collectAsMap().toMap
    // extract the pi values
    val phiS = expectations.flatMap(_._2).filter(_._1.length > 1).aggregateByKey(0.0)(
      seqOp = (s, riT) => {
        s - math.log(1.0 - riT)
      },
      combOp = (s1, s2) => {
        s1 + s2
      }).collect().toList.sortBy(_._2).reverse
    // return the updated dictionary and the average likelihood of texts
    val avglikelihood = dpResult.map(_._1).mean()
    dpResult.unpersist()
    (new Dictionary(thetaS, phiS), avglikelihood)
  }

  /**
    * Dynamic programming on the expectations
    * 关于预期的动态编程
    * @param T           text
    * @param dict        dictionary
    * @param likelihoods likelihoods of T_m (0 <= m <= |T|, T_[|T|] = 1.0)
    * @return niTs and riTs
    */
  def DPExpectations(T: String, dict: Dictionary, likelihoods: Array[BigDecimal]): (Map[String,
    Double], Map[String, Double]) = {
    // expectations of word use frequency: n_i(T_[>=m]) 期望文字使用頻率
    val niTs = new DPCache(tauL, { previous: Double => 1.0 + previous })
    // expectations of word score: r_i(T_[>=m]) 期望文字使用分數
    val riTs = new DPCache(tauL, { previous: Double => 1.0 })
    // dynamic programming from text tail to head
    for (m <- T.length - 1 to 0 by -1) {
      val tLimit = if (m + tauL <= T.length) tauL else T.length - m
      // get all possible cuttings for T_m with one word in head and rest in tail
      val cuttings = Array.range(1, tLimit + 1).flatMap { t =>
        val candidateWord = T.substring(m, m + t)
        if (dict.contains(candidateWord)) {
          val rho = BigDecimal(dict.getTheta(candidateWord)) * likelihoods(m + t) / likelihoods(m)
          Some(candidateWord, t, rho.toDouble)
        } else Nil
      }
      // push cuttings to DP caches
      niTs.push(cuttings)
      riTs.push(cuttings)
    }
    // return T's niS and riS
    (niTs.top, riTs.top)
  }

  /**
    * Prune the dictionary with word use probability (theta) threshold
    *用单词使用概率（θ）阈值修剪字典。
    * @param dict dictionary
    * @return pruned dictionary
    */
  def pruneDictionary(dict: Dictionary): Dictionary = {
    // prune thetaS by use probability threshold
    val smoothMin = dict.thetaS.filter(_._2 > 0).values.min
    val prunedThetaS = dict.thetaS.filter { case (word, theta) =>
      word.length == 1 || theta >= useProbThld
    }.map { case (word, theta) =>
      // smooth single character's zero theta
      if (theta <= 0) word -> smoothMin else word -> theta
    }
    val sumPrunedWordTheta = prunedThetaS.values.sum
    val normalizedWords = prunedThetaS.map { case (word, theta) =>
      word -> theta / sumPrunedWordTheta
    }
    // prune phi according to pruned thetaS
    val prunedPhiS = dict.phiS.flatMap { case (word, phi) =>
      if (normalizedWords.contains(word)) Some(word -> phi) else None
    }
    //return pruned dictionary
    new Dictionary(normalizedWords, prunedPhiS)
  }

  /**
    * Posterior expectation segmentation 后置期望分割
    *
    * @param texts texts to be segmented
    * @param dict  dictionary
    * @return the segmented texts
    */
  def PESegment(texts: RDD[String], dict: Dictionary): RDD[String] = {
    texts.map { T =>
      // calculating the P(T|theta) forwards and backwards respectively
      val forwardLikelihoods = DPLikelihoodsForward(T, dict)
      val backwardLikelihoods = DPLikelihoodsBackward(T, dict)
      // calculating the boundary scores of text
      val boundaryScores = Array.range(1, T.length).map { k =>
        (forwardLikelihoods(k) * backwardLikelihoods(k) / backwardLikelihoods(0)).toDouble
      }
      if (wordBoundaryThld > 0) {
        // segment text if the boundary threshold is set
        new TextSegmentor(T, boundaryScores, wordBoundaryThld).toText()
      } else {
        // segment text using segment tree if the boundary threshold is not set
        new SegmentTree(T, boundaryScores, dict, tauL).toText()
      }
    }
  }

  /**
    * Dynamic programming the likelihoods backwards (應該是右資訊熵)
    *  动态编程的可能性倒推
    *
    *
    * @param T    text        （整段文字）
    * @param dict dictionary  （字典）
    * @return likelihoods
    */
  def DPLikelihoodsBackward(T: String, dict: Dictionary): Array[BigDecimal] = {
    // backward likelihoods: P(T_[>=m]|D,\theta)

    val likelihoods = Array.fill(T.length + 1)(BigDecimal(0.0))// 創建一個陣列，值全部設為 0
    likelihoods(T.length) = BigDecimal(1.0)//整列最後一個值為1
    // dynamic programming from text tail to head
    for (m <- T.length - 1 to 0 by -1) {
      val tLimit = if (m + tauL <= T.length) tauL else T.length - m
      likelihoods(m) = Array.range(1, tLimit + 1).foldLeft(BigDecimal(0.0)) { case (sum, t) =>
        val candidateWord = T.substring(m, m + t)
        if (dict.contains(candidateWord)) {
          sum + dict.getTheta(candidateWord) * likelihoods(m + t)
        } else sum
      }
    }
    likelihoods
  }

  /**
    * Dynamic programming the likelihoods forwards (應該是左資訊熵)
    * 动态编程的可能性前向
    *
    * @param T    text
    * @param dict dictionary
    * @return likelihoods
    */
  def DPLikelihoodsForward(T: String, dict: Dictionary): Array[BigDecimal] = {
    // forward likelihoods: P(T_[<=m]|D,\theta)
    val likelihoods = Array.fill(T.length + 1)(BigDecimal(0.0))
    likelihoods(0) = BigDecimal(1.0)
    // dynamic programming from text head to tail
    for (m <- 1 to T.length) {
      val tLimit = if (m - tauL >= 0) tauL else m

      likelihoods(m) = Array.range(1, tLimit + 1).foldLeft(BigDecimal(0.0)) { case (sum, t) =>
        val candidateWord = T.substring(m - t, m)
        if (dict.contains(candidateWord)) {
          sum + dict.getTheta(candidateWord) * likelihoods(m - t)
        } else sum
      }
    }
    likelihoods
  }
}
