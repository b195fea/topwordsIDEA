package idv.zjh.test

import scala.util.matching.Regex

/**
 * Scala 基礎語法示範
 */
object ScalaRagex {
  def main(args: Array[String]): Unit = {
    demo2()
  }

  /**
   * 運算符
   */
  def demo1(): Unit ={
    var regexText1 = "abl[ae]\\d+"
//    val pattern = new Regex(regexText1)
    val str = "http://tw.gitb/ook.net/scala/scala_regular_expressions.html我https://www.facebook.com/ https://www.youtube.com/watch?v=EABd74ruNQw"
//    println((pattern findAllIn str).mkString(","))

    var regexUrl = "(https?://)?([\\d[a-z]\\.-/]+)"
    val pattern = new Regex(regexUrl)
    println((pattern findAllIn str).mkString(" ||| "))
    val num = """(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-\?=]*)*\/?|([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4})|(\d+\w+)|(\d+)|(\w+)|(\pP|\pS|\s| )+|([\u4E00-\u9FFF]+)""".r
  }


  def demo2(): Unit ={
    var regexUrl = "[\\d|/|-]+"
    val pattern = new Regex(regexUrl)
    var str = "3/15A3-15 2019-02-02"
    println((pattern findAllIn str).mkString(" ||| "))
  }

}
