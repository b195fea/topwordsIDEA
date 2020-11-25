package idv.zjh.test

import scala.collection.mutable.ListBuffer

/**
 * Scala 基礎語法示範
 */
object ScalaCollection {
  def main(args: Array[String]): Unit = {
//    arrayDemo()
    listDemo()
//    listBufferDemo()
//    setDemo()
//    tupleDemo()
//    mapDemo()
//    mapDemo()
  }

  /**
   * 運算符
   */
  def arrayDemo(): Unit ={
    println("------------開始 array 示範------------")
    val numbers = Array(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)
    println(numbers(0))
    println("------------結束 array 示範------------")
  }

  /**
   * 運算符
   */
  def listDemo(): Unit ={
    println("------------開始 list 示範------------")
    val numbers = List(1, 2, 3, 4, 5, ",", 2, 3, 4, 5)
    println(numbers.partition(s => s.equals(3)))

    println(numbers.slice(1,3))
//    println(numbers(3))
//
//    val regexSpecialSymbol = "(\\pP|\\pS|\\s| )+"
//
//    var position = numbers.indexOf(regexSpecialSymbol)
//    println(position)
//
//    var (ys, zs) = numbers.splitAt(position)
//    println(ys)
//    println(zs)
//
//    zs = zs.drop(1)
//    println(zs)

    println("------------結束 list 示範------------")
  }

  /**
   * 運算符
   */
  def listBufferDemo(): Unit ={
    println("------------開始 ListBuffer 示範------------")
    val numbers = new ListBuffer[String]()
    numbers += "Daniel"
    numbers += "Daniel"
    numbers += "Daniel"

    println(numbers)
    println("------------結束 ListBuffer 示範------------")
  }

  /**
   * 運算符
   */
  def setDemo(): Unit ={
    println("------------開始 set 示範------------")
    val numbers = Set(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)
    println(numbers)
    println("------------結束 set 示範------------")
  }

  /**
   * 運算符
   */
  def tupleDemo(): Unit ={
    println("------------開始 tuple 示範------------")
    val hostPort = ("localhost", 80)
    println(hostPort)
    println(hostPort._1)
    println(hostPort._2)
    println("------------結束 tuple 示範------------")
  }


  /**
   * 運算符
   */
  def mapDemo(): Unit ={
    println("------------開始 map 示範------------")
    val map1 = Map(1 -> 2)
    val map2 = Map("foo" -> "bar")
    val map3 = Map(1 -> 2,"foo" -> "bar")
    println(map1)
    println(map2)
    println(map3)
    println("------------結束 map 示範------------")
  }
}
