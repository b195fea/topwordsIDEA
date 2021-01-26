package idv.zjh.demo



import scala.util.matching.Regex

/**
 * Scala 基礎語法示範
 */
object ScalaRagex {
  val regexUrl = "(https?://[\\w-\\.]+(:\\d+)?(\\/[~\\w\\/\\.]*)?(\\?\\S*)?(#\\S*)?)"
  val regexEmail = "([a-zA-Z0-9._%-]+@([a-zA-Z0-9.-]+))"
  val regexNumberSymbol = "([(\\w)(\\d)(/)(\\-)(\\.)]+)"
  val regexSpecialSymbol = "(\\pP|\\pS|\\s| )+"
  val regexChinese = "([\\u4E00-\\u9FFF])"
  val regexOtherSymbol = "(\\W)"
  val regex = regexUrl + "|" + regexEmail + "|" + regexNumberSymbol + "|" + regexSpecialSymbol + "|" + regexChinese + "|" + regexOtherSymbol
  val pattern = new Regex(regex)


  def main(args: Array[String]): Unit = {
//    checkSpecialSymbol()
//    checkChinese()
//    checkOtherSymbol()
    testMatches()


  }

  def testMatches():Unit={
    val result = "TXEE022RDS鍾嘉豪35".matches(regex)
    println(result)
  }

  /**
   * 網頁檢查 運算符
   */
  def checkUrl(): Unit ={
    val regexUrl = "(https?://[\\w-\\.]+(:\\d+)?(\\/[~\\w\\/\\.]*)?(\\?\\S*)?(#\\S*)?)"
    val str = "http://tw.gitb/ook.net/scala/scala_regular_expressions.html我https://www.facebook.com/ https://www.youtube.com/watch?v=EABd74ruNQw"
    val pattern = new Regex(regexUrl)
    println((pattern findAllIn str).mkString(" ||| "))
  }

  /**
   * Email 運算符
   */
  def checkEmail(): Unit ={
    val regexEmail = "([a-zA-Z0-9._%-]+@([a-zA-Z0-9.-]+))"
    val pattern = new Regex(regexEmail)
    var str = "b195fea@163.comddddd.dfgds gsd"
    println((pattern findAllIn str).mkString(" ||| "))
  }

  /**
   * 各類英文數字符號(- . /)組合 109.2.5 ||| 109-2-5 ||| 103/2/5 ||| 2-9
   */
  def checkNumberSymbol(): Unit ={
    val regexNumberSymbol = "([(\\w)(\\d)(/)(\\-)(\\.)]+)"
    val pattern = new Regex(regexNumberSymbol)
    var str = "109.2.5 109-2-5 103/2/5 2-9 AE5567，89E7 dgfdafsgh,6516 sgsdfgd456 5566"
    println((pattern findAllIn str).mkString(" ||| "))
  }

  /**
   * 特殊符號編輯（逗號、句號空白等分隔符）
   */
  def checkSpecialSymbol(): Unit ={
    val regexSpecialSymbol = "(\\pP|\\pS|\\s| )+"
    val pattern = new Regex(regexSpecialSymbol)
    var str = ",dagdag,,A==\"Aﾟдﾟ，，fsg.s。fg，  ，s dg"
    println((pattern findAllIn str).mkString(" ||| "))
  }

  /**
   * 切割所有中日韓字符
   */
  def checkChinese(): Unit ={
    val regexChinese = "([\\u4E00-\\u9FFF])"
    val pattern = new Regex(regexChinese)
    var str = "鍾嘉豪你好ﾟдﾟ"
    println((pattern findAllIn str).mkString(" ||| "))
  }
  /**
   * 所有非單字字符
   */
  def checkOtherSymbol(): Unit ={
    val regexOtherSymbol = "([\\W])"
    val pattern = new Regex(regexOtherSymbol)
    var str = "，，sgsfdg，，鍾嘉d255豪，，aaa，羅莎莉亞很可愛ﾟдﾟ，不知道是羅莎莉亞比較可愛，還是莉莉婭比較可愛"
    println((pattern findAllIn str).mkString(" ||| "))
  }
}
