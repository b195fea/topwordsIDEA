package idv.zjh.prcatice

import idv.zjh.prcatice.Temp
import idv.zjh.prcatice.TestApplyClass

import scala.util.matching.Regex

case object TestObject{
  def main(args: Array[String]): Unit = {
    test2()
    println("\n\n\n\n\n\n\n-------------------------------------")
    test1()
  }

  def test1(): Unit ={
    var T = List("這", "是", "我", "的", "信", "箱")
    println(T.length)
    val likelihoods = Array.fill(T.length + 1)(BigDecimal(0.0))
//    likelihoods.foreach(value =>{
//      println(value)
//    })
    likelihoods(0) = BigDecimal(1.0)
    for (m <- T.length - 1 to 0 by -1) {

      val tLimit = if (m + 20 <= T.length) 20 else T.length - m
      println("tLimit:"+tLimit)
      var arrayRange = Array.range(1, tLimit + 1)
      println("arrayRange:"+arrayRange.length)
      likelihoods(m) = arrayRange.foldLeft(BigDecimal(0.0)) { case (sum, t) =>
        println("T:"+T)
        println("sum:"+sum)
        println("m:"+m)
        println("t:"+t)
        val candidateWord = getWord(T,m, m + t)
        println("candidateWord:[" + candidateWord + "]")
        likelihoods(m + t)
      }
    }

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
//    var word = "tauL：文字最長為多少(之前設為10)"
//    var tauL = 10
//    for (m <- T.length - 1 to 0 by -1) {
//      println("m:" + m)
//      val tLimit = if (m + tauL <= T.length) tauL else T.length - m
//      println(tLimit)
//    }

    //println("likelihoods:[" + likelihoods + "],")

//    var likehoods = Array.range(1,  10).foldLeft(BigDecimal(0.0)) { case (sum, t) =>
//      println("Sum:[" + sum + "]," + "t:[" + t + "]")
//      sum
//    }
//    println("likehoods:[" + likehoods + "],")
  }

  def test2(): Array[BigDecimal] = {
    // backward likelihoods: P(T_[>=m]|D,\theta)
    var T = "這是我的信箱"
    val likelihoods = Array.fill(T.length + 1)(BigDecimal(0.0))
    likelihoods(T.length) = BigDecimal(1.0)
    // dynamic programming from text tail to head
    for (m <- T.length - 1 to 0 by -1) {
      val tLimit = if (m + 20 <= T.length) 20 else T.length - m
      println("tLimit:"+tLimit)
      var arrayRange = Array.range(1, tLimit + 1)
      println("arrayRange:"+arrayRange.length)
      likelihoods(m) = arrayRange.foldLeft(BigDecimal(0.0)) { case (sum, t) =>
        val candidateWord = T.substring(m, m + t)
        println("T:"+T)
        println("sum:"+sum)
        println("m:"+m)
        println("t:"+t)
        println("candidateWord:[" + candidateWord + "]")
        likelihoods(m + t)
      }
    }
    likelihoods
  }

  def getWord(listString:List[String] ,begin:Int ,end:Int ): String = {
    var result = ""
    try{

      var maxLength = listString.length
      for( position <- begin until end){
        result = result + listString(position)
      }

    }catch{
      case e: IndexOutOfBoundsException => {
        println("listString:"+ listString)
        println("begin:"+ begin)
        println("end:"+ end)
        throw new IndexOutOfBoundsException("You are not eligible")
      }
    }
    result
  }

}
