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
 *
 */
class Preprocessing() extends Serializable {
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

  def run(corpus: RDD[String]): RDD[List[String]] = {
    val pattern = new Regex(regex)
    // importing spark implicits

    var rtnRdd = corpus.flatMap { T =>
      // 使用标点和空格将段落分成几段文字
      // split the paragraph into several texts using punctuations and spaces
      var s = T.split("[,|。|，|：|!|、|？|　]").map(_.trim)
      s
    }.map(text => {
      println("Text:" + text)
      // 將文字切成一個一個字元，並將網址、數字、英文等視為一個字元
      (pattern findAllIn text).toList
    })


    rtnRdd

    rtnRdd.foreach(T => {
      println(T)
    })
    rtnRdd
  }
}
