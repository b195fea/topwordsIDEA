package idv.zjh.topwords

import idv.zjh.topwords.test.TestTopWords.{regex, regexUrl}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel

import scala.collection.mutable.ListBuffer
import scala.util.matching.Regex

/**
 * Created by qfeng on 16-7-6.
 */

/**
 *
 * Preprocessing method of corpus 語料庫的預處理方法
 *
 * @param textLenThld theshold of text length
 */
class Preprocessing(private val textLenThld: Int) extends Serializable {
  val regexUrl = "(https?://[\\w-\\.]+(:\\d+)?(\\/[~\\w\\/\\.]*)?(\\?\\S*)?(#\\S*)?)"
  val regexEmail = "([a-zA-Z0-9._%-]+@([a-zA-Z0-9.-]+))"
  val regexNumberSymbol = "([(\\w)(\\d)(/)(\\-)(\\.)]+)"
  val regexSpecialSymbol = "(\\pP|\\pS|\\s| )+"
  val regexChinese = "([\\u4E00-\\u9FFF])"
  val regexOtherSymbol = "(\\W)"
  val regex = regexUrl + "|" + regexEmail + "|" + regexNumberSymbol + "|" + regexSpecialSymbol + "|" + regexChinese + "|" + regexOtherSymbol
  val pattern = new Regex(regex)


  /**
   * Preprocessing method of corpus
   *
   * @param corpus corpus
   * @return preprocessed
   *         corpus
   */

  def run(corpus: RDD[String]): RDD[String] = {
    val pattern = new Regex(regex)
    // importing spark implicits

    var rtnRdd = corpus.flatMap { T =>
      // 使用标点和空格将段落分成几段文字
      // split the paragraph into several texts using punctuations and spaces
      var s = T.split("[,|。|，|：|!|、|？|　]").map(_.trim)
      s
    }.map(text => {
      //println("Text:" + text)
      // 將文字切成一個一個字元，並將網址、數字、英文等視為一個字元
      (pattern findAllIn text).toList
    }).flatMap(listString => {
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
      //println("permutations:" + permutations)
      permutations
    })

    rtnRdd
  }
}
