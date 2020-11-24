package idv.zjh.test

/**
 * Scala 基礎語法示範
 */
object ScalaBasic {
  def main(args: Array[String]): Unit = {
    demo1()
  }

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
