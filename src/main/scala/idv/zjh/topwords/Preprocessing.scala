package idv.zjh.topwords

import idv.zjh.topwords.test.TestTopWords.regexUrl
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel

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


  /**
   * Preprocessing method of corpus
   *
   * @param corpus corpus
   * @return preprocessed corpus
   */
  def run(corpus: RDD[String]): RDD[String] = {
    // importing spark implicits
    corpus.flatMap { T =>
      // split the paragraph into several texts using punctuations and spaces
      T.split("\\pP|\\pS|\\s|　").map(_.trim)
    }.filter(_.length > 1).flatMap { T =>
      // split text according to text length threshold
      if (T.length > textLenThld) {
        var splits = List[StringBuilder]() ::: List(new StringBuilder())
        T.foreach { c =>
          if ((splits.last += c).length >= textLenThld) {
            splits = splits ::: List(new StringBuilder())
          }
        }
        // return split texts
        splits.map(_.toString())
      } else List(T)
    }
  }
//
//  /**
//    * Preprocessing method of corpus
//    *
//    * @param corpus corpus
//    * @return preprocessed
//   *         corpus
//    */
//
//  def run(corpus: RDD[String]): RDD[String] = {
//    val pattern = new Regex(regex)
//    // importing spark implicits
//
//    corpus.flatMap { T =>
//      // 使用标点和空格将段落分成几段文字
//      // split the paragraph into several texts using punctuations and spaces
////      T.split("\\pP|\\pS|\\s|　").map(_.trim)
//      var s = T.split("[,|。|，|：|!|、|？|　]").map(_.trim)
//      print(s)
//      s
//    }.filter(_.length > 1).flatMap { T =>
//      //根据文本长度阈值分割文本
//      // split text according to text length threshold
//      if (T.length > textLenThld) {
//        var splits = List[StringBuilder]() ::: List(new StringBuilder())
//        T.foreach { c =>
//          if ((splits.last += c).length >= textLenThld) {
//            splits = splits ::: List(new StringBuilder())
//          }
//        }
//        // return split texts
//        splits.map(_.toString())
//      } else List(T)
//    }
//  }
}
