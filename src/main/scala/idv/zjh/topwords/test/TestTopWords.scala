package idv.zjh.topwords.test

import idv.zjh.test.scalaTest.readFile
import idv.zjh.topwords.Preprocessing
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

case object TestTopWords {
  val regexUrl = "(https?://[\\w-\\.]+(:\\d+)?(\\/[~\\w\\/\\.]*)?(\\?\\S*)?(#\\S*)?)"
  val regexEmail = "([a-zA-Z0-9._%-]+@([a-zA-Z0-9.-]+))"
  val regexNumberSymbol = "([(\\w)(\\d)(/)(\\-)(\\.)]+)"
  val regexSpecialSymbol = "(\\pP|\\pS|\\s| )+"
  val regexChinese = "([\\u4E00-\\u9FFF])"
  val regexOtherSymbol = "(\\W)"
  val regex = regexUrl + "|" + regexEmail + "|" + regexNumberSymbol + "|" + regexSpecialSymbol + "|" + regexChinese + "|" + regexOtherSymbol
  val pattern = new Regex(regex)

  def main(args: Array[String]): Unit = {
    val regex = regexUrl + "|" + regexEmail + "|" + regexNumberSymbol + "|" + regexSpecialSymbol + "|" + regexChinese + "|" + regexOtherSymbol
    val pattern = new Regex(regex)
    val inputFile = "test_data/test4.txt"
    val corpus = readFile(inputFile) // 取得 RDD

    val tauL = 15

    corpus.flatMap { T =>
      // 使用标点和空格将段落分成几段文字
      // split the paragraph into several texts using punctuations and spaces
      var s = T.split("[,|。|，|：|!|、|？|　]").map(_.trim)
      s
    }.map(text => {
      println("Text:" + text)
      // 將文字切成一個一個字元，並將網址、數字、英文等視為一個字元
      (pattern findAllIn text).toList
    }).flatMap(listString => {
      // 依字元建立所有可能的組合（你很漂亮：[你]、[很]、[漂]、[亮]、[你很]、[很漂]、[漂亮]、[你很漂]、[很漂亮]、[你很漂亮]）
      val permutations = ListBuffer[String]()
      for (wordLength <- 1 to tauL) { // to 包含 tauL
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
      println("permutations:" + permutations)
      permutations
    }).map(_ -> 1).reduceByKey(_ + _).foreach(s => {
      // .map(_ -> 1)         將所有組合字串設定值為1
      // .reduceByKey(_ + _)  將所有key的值相加，記錄出現次數
      println(s)
    })
  }

  /**
   * 讀取文字檔案 並回傳RDD
   *
   * @return
   */
  def readFile(inputFile: String): RDD[String] = {
    // master 服務器網址
    val spark = SparkSession.builder().master("local[1]").appName(this.getClass.toString).getOrCreate()
    val corpus = spark.sparkContext.textFile(inputFile)
    corpus.cache()
    corpus
  }
}
