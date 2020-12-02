package idv.zjh.test

import idv.zjh.topwords.Preprocessing
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel

import scala.collection.mutable.ListBuffer

object scalaTest {
  def main(args: Array[String]): Unit = {
    test()
  }

  def test(): Unit ={
    val likelihoods = Array.fill(10)(BigDecimal(0.0))
    println(likelihoods.length)
  }




  def run(): Unit ={
    val inputFile = "test_data/bh3_test2.txt"
    // 取得 RDD
    val coups = readFile(inputFile)
    // 將文字分段
    var dictionaryOver = splitCorpus(coups,50)
    println("start")
    dictionaryOver.foreach(text => {
      println("-----------------------")
      println(text)
      println("-----------------------")
    })
  }

  def fold1(): Unit ={
    val nums = List(1,2,3,4,5)
    var initialValue:Int = 0;
    var sum = nums.fold(initialValue){
      (accumulator,currentElementBeingIterated) => accumulator + currentElementBeingIterated
    }
    println(sum) //prints 15 because 0+1+2+3+4+5 = 15
  }

  def fold2(): Unit ={
    def sum(x: Int, y: Int) = x+ y
    val nums = List(1, 2, 3, 4, 5)
    var initialValue: Int = 0
    val sum2 = nums.fold(initialValue)(sum)
    println(sum2) // prints 15 because 0 + 1 + 2 + 3 + 4 + 5 = 15
  }

  /**
   * 執行預處理， 以標點符號分段，並回傳RDD
   * @param corpus
   * @return
   */
  def splitCorpus(corpus: RDD[String],textLenThld :Int): RDD[String] ={
    // importing spark implicits
    corpus.flatMap { T =>
      // 使用标点和空格将段落分成几段文字
      // split the paragraph into several texts using punctuations and spaces
      T.split("\\pP|\\pS|\\s|　").map(_.trim)
    }.filter(_.length > 1).flatMap { T =>
      //根据文本长度阈值分割文本
      // split text according to text length threshold
      if (T.length > textLenThld) {
        var splits = List[StringBuilder]() ::: List(new StringBuilder())
        T.foreach { c =>
          if ((splits.last += c).length >= textLenThld) {
            splits = splits ::: List(new StringBuilder())
          }
        }
        // return split texts
        splits.map(_.toString())
      } else List(T)
    }
  }


  /**
   * 讀取文字檔案
   * @return
   */
  def readFile(inputFile:String):RDD[String] ={
//    val texts = new Preprocessing(textLenThld).run(corpus).persist(StorageLevel.MEMORY_AND_DISK_SER_2)
    // master 服務器網址
    val spark = SparkSession.builder().master("local[1]").appName(this.getClass.toString).getOrCreate()
    val corpus = spark.sparkContext.textFile(inputFile)
    //corpus.foreach(text => println(text))
    corpus.cache()
    //println("count:" + corpus.count())
    return corpus
  }



  /**
   * 執行正規表示法
   */
  def runRegex(): Unit ={
    //    var text = "鍾嘉豪真 ,的，==，dd，很棒".split("\\pP|\\pS|\\s|　")
    //[\\pP]
    //[\s]：空白、Tab
    //[　]：全型空白
    var text = "AA中BB，CC,DD".split("\\PP")
    for(i <- 0 to text.length-1) {
      println(text(i))
    }
  }
}