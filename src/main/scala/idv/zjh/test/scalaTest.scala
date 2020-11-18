package idv.zjh.test

object scalaTest {
  def addInt(a:Int,b:Int) : Int = {
    var sum:Int = 0
    sum = a + b
    return sum
  }
  
  def main(args: Array[String]): Unit = {
    var text = "鍾嘉豪真,的，，，很棒".split("\\pP|\\pS|\\s|　")
    println(text.toString)
    for(i <- 0 to text.length-1)
      println(text(i))
    var text2 = "a-b-c".split("-", 2)
    println(text2)
    for(i <- 0 to text2.length-1)
      println(text2(i))
  }
}