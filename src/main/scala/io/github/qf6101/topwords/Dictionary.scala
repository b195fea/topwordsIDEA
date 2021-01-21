package io.github.qf6101.topwords

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
  * @param thetaS word use probability
  * @param phiS   word significance
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
    * Generate an overcomplete dictionary in the initial step 產生一個過於龐大的詞典
    * Note: using the brute force strategy which however need to use the sequential Apriori strategy instead 使用蛮力策略，但需要使用顺序Apriori策略来代替
    *
    * @param corpus a set of texts                原始文本
    * @param tauL   threshold of word length      詞彙長度
    * @param tauF   threshold of word frequency   詞彙頻率
    * @return an overcomplete dictionary          回傳過於龐大的此地那
    */
  def apply(corpus: RDD[String],
            tauL: Int,
            tauF: Int,
            useProbThld: Double): Dictionary = {
    //enumerate all the possible words: corpus -> words
    val words = corpus.flatMap { text =>
      val permutations = ListBuffer[String]()
      for (i <- 1 to tauL) {
        for (j <- 0 until text.length) {
          if (j + i <= text.length) permutations += text.substring(j, j + i)
        }
      }
      permutations
    }.map(_ -> 1).reduceByKey(_ + _).filter { case (word, freq) =>
      // leave the single characters in dictionary for smoothing reason even if they are low frequency
      word.length == 1 || freq >= tauF
    }.persist(StorageLevel.MEMORY_AND_DISK_SER_2)
    //filter words by the use probability threshold: words -> prunedWords
    val sumWordFreq = words.map(_._2).sum()

    // 將 words 資料存到  prunedWords
    val prunedWords = words.map { case (word, freq) =>
      (word, freq, freq / sumWordFreq)
    }.filter { case (word, _, theta) =>
      // leave the single characters in dictionary for smoothing reason even if they have small theta
      word.length == 1 || theta >= useProbThld
    }

    words.unpersist() // 抹除該標記，釋放緩存
    prunedWords.persist(StorageLevel.MEMORY_AND_DISK_SER_2)// 把超出記憶體的部分存在硬碟中，而不是每次重新計算
    //normalize the word use probability: prunedWords -> normalizedWords
    val sumPrunedWordFreq = prunedWords.map(_._2).sum()
    val normalizedWords = prunedWords.map { case (word, freq, _) =>
      word -> freq / sumPrunedWordFreq
    }.collectAsMap().toMap
    prunedWords.unpersist()
    //return the overcomplete dictionary: normalizedWords -> dictionary
    new Dictionary(normalizedWords)
  }
}
