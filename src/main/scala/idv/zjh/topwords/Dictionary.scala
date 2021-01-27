package idv.zjh.topwords

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

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

  val regexUrl = "(https?://[\\w-\\.]+(:\\d+)?(\\/[~\\w\\/\\.]*)?(\\?\\S*)?(#\\S*)?)"
  val regexEmail = "([a-zA-Z0-9._%-]+@([a-zA-Z0-9.-]+))"
  val regexNumberSymbol = "([(\\w)(\\d)(/)(\\-)(\\.)]+)"
  val regexSpecialSymbol = "(\\pP|\\pS|\\s| )+"
  val regexChinese = "([\\u4E00-\\u9FFF])"
  val regexOtherSymbol = "(\\W)"
  val regex = regexUrl + "|" + regexEmail + "|" + regexNumberSymbol + "|" + regexSpecialSymbol + "|" + regexChinese + "|" + regexOtherSymbol
  val pattern = new Regex(regex)


  /**
   * Generate an overcomplete dictionary in the initial step
   * Note: using the brute force strategy which however need to use the sequential Apriori strategy instead
   *
   * @param corpus a set of texts               截成一段一段的文檔
   * @param tauL   threshold of word length     詞長度
   * @param tauF   threshold of word frequency  詞頻率
   * @return an overcomplete dictionary
   */
  def apply(corpus: RDD[List[String]],
            tauL: Int,
            tauF: Int,
            useProbThld: Double,
            textLenThld: Int): Dictionary = {

    var iter = 0
    val words = corpus.flatMap(listString => {
          // 依字元建立所有可能的組合（你很漂亮：[你]、[很]、[漂]、[亮]、[你很]、[很漂]、[漂亮]、[你很漂]、[很漂亮]、[你很漂亮]）
          val permutations = ListBuffer[String]()
          for (wordLength <- 1 to textLenThld) { // to 包含 tauL
            for (wordPosition <- 0 until listString.length) { // until 不包含 text.length
              if (wordPosition + wordLength <= listString.length) {
                var temp = ""
                for (i <- 1 to wordLength) {
                  temp += listString(wordPosition + i - 1)
                }
                permutations += temp
              }
            }
          }
          iter = iter + 1
          println("permutations:" + iter)
//          println("permutations:" + permutations)
          permutations
      //enumerate all the possible words: corpus -> words
      // 第一步驟 ： 將文字長度為1以及出現次數大於閾值的所有參數，加入詞典。(列舉所有可能的詞)
        }).map(_ -> 1).reduceByKey(_ + _).filter { case (word, freq) =>
      // leave the single characters in dictionary for smoothing reason even if they are low frequency
//      println("word:"+ word)
      // 1.單字加入字典
      // 2.符合正規表示法，如連續數字、日期、網址、英文、連續特殊符號
      // 3.
      word.length == 1 || word.matches(regex) ||freq >= tauF
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
//    println("normalizedWords:" + normalizedWords)
    var d = new Dictionary(normalizedWords)
    println("Dictionary:" + d)
    d
  }
}
