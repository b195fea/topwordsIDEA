package idv.zjh.prcatice

case object TestObject{
  def main(args: Array[String]): Unit = {
//  不可變量
    val hello:String = "hello World"
    println(hello)
    var helloscala:String = "hello Scala"
    println(helloscala)
    helloscala = "hello World"
    println(helloscala)

    var hl = "hello"
    println(hl)

    // 元組變量，可以放不同類型的參數
    var tuple = ("hello","China",1,0.01)
    println(tuple)

    // if 表達式，相當於java 的三元表達式
    var x = 5
    var y = if(x==0) 0 else x+x
    println(y)
  }
}
