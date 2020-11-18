package idv.zjh.prcatice

class Temp {
  var value = 0
  def increments(step:Int):Unit = {value += step}
  def current():Int = {value}

  def this(value2:Int){
    this()
    value = value2
  }

  def main(args: Array[String]) {
    var palindrome = "www.runoob.com";
    var len = palindrome.length();
    println( "String Length is : " + len );
  }
}
