package idv.zjh.test

/**
 * Scala 基礎語法示範
 */
object ScalaBasic {
  def main(args: Array[String]): Unit = {
    demo2()
  }

  def demo2(): Unit = {
    // by 表示每一次 -2
    for (m <- 10 to 0 by -2) {
      println(m)
    }
    // 預設每一次 +1
    for (m <- 0 to 10) {
      println(m)
    }
  }

  /**
   * 運算符
   */
  def demo1(): Unit ={
    val site1: List[String] = List("Runoob", "Google", "Baidu")
    val site2: List[String] = List("Runoob1", "Google2", "Baidu3")

    var siteAnswer = site1 :: site2
    println("::" + siteAnswer)// 向尾部追加元素  List(List(Runoob, Google, Baidu), Runoob1, Google2, Baidu3)
    siteAnswer = site1 +: site2
    println("+:" + siteAnswer)// 向尾部追加元素 List(List(Runoob, Google, Baidu), Runoob1, Google2, Baidu3)
    siteAnswer = site1 :+ site2
    println(":+" + siteAnswer)// 向頭部追加元素 List(Runoob, Google, Baidu, List(Runoob1, Google2, Baidu3))
    siteAnswer = site1 ::: site2
    println(siteAnswer) // 元素串接 List(Runoob, Google, Baidu, List(Runoob1, Google2, Baidu3))
    siteAnswer = site1 ++ site2
    println(siteAnswer) // 元素串接 List(Runoob, Google, Baidu, List(Runoob1, Google2, Baidu3))

  }
}
