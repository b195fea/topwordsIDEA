package idv.zjh.test

/**
 * Scala 基礎語法示範
 */
object ScalaFunction {
  def main(args: Array[String]): Unit = {
    var a = 0.0000
    if(a==0){
      println(true)
    }else{
      println(false)
    }

//    demo1()
  }

  def demo1(): Unit ={
    val site1: List[String] = List("Runoob", "Google", "Baidu")
    val site2: List[String] = List("Runoob1", "Google2", "Baidu3")

    println(site1(0) + site1(1))

//    var siteAnswer = site1 :: site2
//    println("::" + siteAnswer)// 向尾部追加元素  List(List(Runoob, Google, Baidu), Runoob1, Google2, Baidu3)
//    siteAnswer = site1 +: site2
//    println("+:" + siteAnswer)// 向尾部追加元素 List(List(Runoob, Google, Baidu), Runoob1, Google2, Baidu3)
//    siteAnswer = site1 :+ site2
//    println(":+" + siteAnswer)// 向頭部追加元素 List(Runoob, Google, Baidu, List(Runoob1, Google2, Baidu3))
//    siteAnswer = site1 ::: site2
//    println(siteAnswer) // 元素串接 List(Runoob, Google, Baidu, List(Runoob1, Google2, Baidu3))
//    siteAnswer = site1 ++ site2
//    println(siteAnswer) // 元素串接 List(Runoob, Google, Baidu, List(Runoob1, Google2, Baidu3))
  }
}
