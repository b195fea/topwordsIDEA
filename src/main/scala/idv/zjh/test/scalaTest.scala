package idv.zjh.test

object scalaTest {
  def addInt(a:Int,b:Int) : Int = {
    var sum:Int = 0
    sum = a + b
    return sum
  }
  
  def main(args: Array[String]): Unit = {
    "鍾嘉豪真的很棒".split("\\pP|\\pS|\\s|　")
  }
}