package idv.zjh.prcatice

import idv.zjh.prcatice.Temp
import idv.zjh.prcatice.TestApplyClass

import scala.util.matching.Regex

case object TestObject{
  def main(args: Array[String]): Unit = {
    val pattern = new Regex("(S|s)cala")  // 首字母可以是大写 S 或小写 s
    val str = "Scala is scalable and cool"

    println((pattern findAllIn str).mkString(","))   // 使用逗号 , 连接返回结果

    var temp = new Temp
    temp.value = 5
    temp.increments(10)
    println(temp.current())
    temp.main(null)

    var temp2 = new Temp
    var temp3 = new Temp(1000)
    println(temp3.current())

    var testapplyClass = new TestApplyClass
    testapplyClass("123")
    //testapplyClass.apply("123")

//  不可變量
//    val hello:String = "hello World"
//    println(hello)
//    var helloscala:String = "hello Scala"
//    println(helloscala)
//    helloscala = "hello World"
//    println(helloscala)
//
//    var hl = "hello"
//    println(hl)
//
//    // 元組變量，可以放不同類型的參數
//    var tuple = ("hello","China",1,0.01)
//    println(tuple)
//
//    // if 表達式，相當於java 的三元表達式
//    var x = 5
//    var y = if(x==0) 0 else x+x
//    println(y)
  }
}
