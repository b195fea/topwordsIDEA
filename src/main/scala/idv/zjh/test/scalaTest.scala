package idv.zjh.test

import idv.zjh.topwords.Preprocessing
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel

object scalaTest {
  def addInt(a:Int,b:Int) : Int = {
    var sum:Int = 0
    sum = a + b
    return sum
  }

  def main(args: Array[String]): Unit = {
    readFile()
//    runRegex()
  }

  /**
   * 讀取文字檔案
   * @return
   */
  def readFile():RDD[String] ={
//    val texts = new Preprocessing(textLenThld).run(corpus).persist(StorageLevel.MEMORY_AND_DISK_SER_2)
    // master 服務器網址
    val spark = SparkSession.builder().master("local[1]").appName(this.getClass.toString).getOrCreate()
    val inputFile = "test_data/bh3_test.txt"
    val corpus = spark.sparkContext.textFile(inputFile)
    corpus.foreach(text => println(text))
    corpus.cache()
    println("count:" + corpus.count())


  }

  /**
   * 執行正規表示法
   */
  def runRegex(): Unit ={
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