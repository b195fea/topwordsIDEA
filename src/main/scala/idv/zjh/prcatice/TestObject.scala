package idv.zjh.prcatice

import idv.zjh.prcatice.Temp
import idv.zjh.prcatice.TestApplyClass

import scala.util.matching.Regex

case object TestObject{
  def main(args: Array[String]): Unit = {
    test1()
  }

  def test1(): Unit ={
    var likehoods = Array.range(1,  10).foldLeft(BigDecimal(0.0)) { case (sum, t) =>
      println("Sum:[" + sum + "]," + "t:[" + t + "]")
      sum
    }
    println("likehoods:[" + likehoods + "],")
  }
}
