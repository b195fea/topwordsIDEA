package idv.zjh.topwords

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel

import scala.collection.mutable.ListBuffer

/**
 * Created by qfeng on 16-6-30.
 */

/**
 * Dictionary (initial state is overcomplete for EM)
 *
 * @param thetaS word use probability  文字使用頻率
 * @param phiS   word significance     文字重要性
 */
class Dictionary(val thetaS: Map[String, Double],
                 val phiS: List[(String, Double)] = Nil) extends Serializable {
  /**
   * Query certain word's theta
   *
   * @param word query word
   * @return query word's theta (returns zero if no such word found)
   */
  def getTheta(word: String): Double = {
    thetaS.getOrElse(word, 0.0)
  }

  /**
   * Test if certain word is in dictionary
   *
   * @param word query word
   * @return whether the query word in dictionary
   */
  def contains(word: String): Boolean = {
    thetaS.contains(word)
  }

  /**
   * Save the dictionary to file
   *
   * @param dictFile saving destination
   */
  def save(dictFile: String): Unit = {
    val sc = SparkContext.getOrCreate()
    // saving theta values
    sc.parallelize(thetaS.filter(_._1.length > 1).toList.sortBy(_._2).reverse).repartition(1).saveAsTextFile(dictFile + "/thetaS")
    // saving phi values
    if (phiS != Nil) sc.parallelize(phiS).repartition(1).saveAsTextFile(dictFile + "/phiS")
  }
}

object Dictionary extends Serializable {
  /**
   * Generate an overcomplete dictionary in the initial step
   * Note: using the brute force strategy which however need to use the sequential Apriori strategy instead
   *
   * @param corpus a set of texts               截成一段一段的文檔
   * @param tauL   threshold of word length     詞長度
   * @param tauF   threshold of word frequency  詞頻率
   * @return an overcomplete dictionary
   */
  def apply(corpus: RDD[String],
            tauL: Int,
            tauF: Int,
            useProbThld: Double): Dictionary = {


    //enumerate all the possible words: corpus -> words
    // 第一步驟 ： 將文字長度為1以及出現次數大於閾值的所有參數，加入詞典。(列舉所有可能的詞)
    val words = corpus.map(_ -> 1).reduceByKey(_ + _).filter { case (word, freq) =>
      // leave the single characters in dictionary for smoothing reason even if they are low frequency
      word.length == 1 || freq >= tauF
    }.persist(StorageLevel.MEMORY_AND_DISK_SER_2)



    //filter words by the use probability threshold: words -> prunedWords
    // 第二步驟：單字長度為1的詞，並計算出現頻率大於1E - 8 次方，視為單詞
    val sumWordFreq = words.map(_._2).sum()
    val prunedWords = words.map { case (word, freq) =>
      (word, freq, freq / sumWordFreq)
    }.filter { case (word, _, theta) =>
      // leave the single characters in dictionary for smoothing reason even if they have small theta
      // useProbThld 1E-8 (保留字為1 且頻率大於1E - 8 次方)
      word.length == 1 || theta >= useProbThld
    }

    words.unpersist() // 抹除該標記，釋放緩存
    prunedWords.persist(StorageLevel.MEMORY_AND_DISK_SER_2)// 把超出記憶體的部分存在硬碟中，而不是每次重新計算
    //normalize the word use probability: prunedWords -> normalizedWords
    // _._2 表示取 第二個 map值(字數加總)
    val sumPrunedWordFreq = prunedWords.map(_._2).sum()
    val normalizedWords = prunedWords.map { case (word, freq, _) =>
      word -> freq / sumPrunedWordFreq
    }.collectAsMap().toMap
    prunedWords.unpersist()
    //return the overcomplete dictionary: normalizedWords -> dictionary
    println("normalizedWords:" + normalizedWords)
    var d = new Dictionary(normalizedWords)
    println("Dictionary:" + d)
    d
  }
}
