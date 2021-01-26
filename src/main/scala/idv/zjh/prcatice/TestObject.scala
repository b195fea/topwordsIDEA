package idv.zjh.prcatice

import idv.zjh.prcatice.Temp
import idv.zjh.prcatice.TestApplyClass

import scala.util.matching.Regex

case object TestObject{
  def main(args: Array[String]): Unit = {
    test1()
  }

  def test1(): Unit ={
    var words = List("這", "是", "我", "的", "信", "箱","b195fea@gmail.com")

//    val nums = List(1,2,3,4,5)
//    var initialValue:Int = 0;
//    var sum = nums.fold(initialValue){
//      (accumulator,currentElementBeingIterated) => accumulator + currentElementBeingIterated
//    }
//    println(sum) //prints 15 because 0+1+2+3+4+5 = 15

//    var likelihoods = Array(1, 2, 3, 4)
//    var sum = likelihoods.foldLeft(10) { case (left, right) =>
//      println("[left]:"+left+"[right]" + right)
//      left * right
//    }
//    println("sum:"+sum)
//    likelihoods.foreach(T =>{
//      println(T)
//    })


    // tauL：文字最長為多少(之前設為10)
    // 句子為20
    // 如果句子小於
    var T = "tauL：文字最長為多少(之前設為10)"
    var tauL = 10
    for (m <- T.length - 1 to 0 by -1) {
      println("m:" + m)
      val tLimit = if (m + tauL <= T.length) tauL else T.length - m
      println(tLimit)
    }

    //println("likelihoods:[" + likelihoods + "],")

//    var likehoods = Array.range(1,  10).foldLeft(BigDecimal(0.0)) { case (sum, t) =>
//      println("Sum:[" + sum + "]," + "t:[" + t + "]")
//      sum
//    }
//    println("likehoods:[" + likehoods + "],")
  }
}
