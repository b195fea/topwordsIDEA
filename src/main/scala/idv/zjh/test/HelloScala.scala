package idv.zjh.test

import scala.collection.mutable.HashMap

object HelloScala {
  def addInt(a:Int,b:Int) : Int = {
    var sum:Int = 0
    sum = a + b
    return sum
  }
  
  def main(args: Array[String]): Unit = {
    println("Hello Scala!")
    
    val s = "hello"
    println(s)
    
    var intType1 = 5
    var intType2 = 123
    println(addInt(intType1,intType2))
    
    val multiplier =(i:Int) => i *10
    println(multiplier(5))
    
    var z1 = Array("Zara", "Nuha", "Ayan")
    for(i<-1 to (z1.length-1)){
      println(z1(i))
    }
    var z2 = new Array[String](3)
    z2(0) = "firstArray"
    z2(1) = "secondArray"
    z2(2) = "thirdArray"
    for( x <- z2){
      println(x)
    }
    
    
  }
}