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

  def main(args: Array[String]): Unit = {
    val regex = regexUrl + "|" + regexEmail + "|" + regexNumberSymbol + "|" + regexSpecialSymbol + "|" + regexChinese + "|" + regexOtherSymbol
    val pattern = new Regex(regex)
    val inputFile = "test_data/bh3_test2.txt"
    val coups = readFile(inputFile)// 取得 RDD

    val tauL = 30

    coups.flatMap { T =>

      // 使用标点和空格将段落分成几段文字
      // split the paragraph into several texts using punctuations and spaces
      var s  = T.split("[,|。|，|：|!|、|？|　]").map(_.trim)

      s
    }.map(text => {
      println("Text:" + text)
      // 將字串轉變成文字
      (pattern findAllIn text).toList
    }).flatMap(listString => {
      val permutations = ListBuffer[String]()
      for (wordLength <- 1 to tauL) {// to 包含 tauL
        for (wordPosition <- 0 until listString.length) {// until 不包含 text.length
          if (wordPosition + wordLength <= listString.length) {
            var temp = ""
            for (i <- 1 to wordLength){
              temp += listString(wordPosition + i - 1)
            }
            permutations += temp
          }
        }
      }
      println("permutations:"+permutations)
      permutations

    }).map(_ -> 1).reduceByKey(_ + _).foreach(s => {
      println(s)
    })
  }



  /**
   * 讀取文字檔案 並回傳RDD
   * @return
   */
  def readFile(inputFile:String):RDD[String] ={
    // master 服務器網址
    val spark = SparkSession.builder().master("local[1]").appName(this.getClass.toString).getOrCreate()
    val corpus = spark.sparkContext.textFile(inputFile)
    corpus.cache()
    corpus
  }



}
