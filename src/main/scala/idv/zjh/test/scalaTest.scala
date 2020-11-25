package idv.zjh.test

import idv.zjh.topwords.Preprocessing
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel

import scala.collection.mutable.ListBuffer

object scalaTest {
  def main(args: Array[String]): Unit = {
//    var ragex1 = "(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-\?=]*)*\/?"
//    var ragex2 = "([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4})"
//    var ragex3 = "(\d+\w+)"
//    var reagex4 = "(\d+)"


//    (\d+)|(\w+)|(\pP|\pS|\s| )+|([\u4E00-\u9FFF]

    var text = "https://www.youtube.com/watch?v=EABd74ruNQw羅莎莉亞OTZ很可愛ﾟдﾟb195fea@gmail.com，不知道是羅莎莉亞比較可愛aadd，，，還      是莉A1345B莉婭比ＳＳ較可愛123456個蘋ω果3/15ω"
    val num = """(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-\?=]*)*\/?|([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4})|[\d|/|-]+|(\d+\w+)|(\d+)|(\w+)|(\pP|\pS|\s| )+|([\u4E00-\u9FFF]|\W+)""".r
    val all = (num findAllIn text).toList
    var listBuffer = new ListBuffer[String]()

    var temp = new String()
    all.foreach(text => {
        temp = temp + text
        if(text.equals("，")){
          listBuffer += temp
          temp = ""
        }
    })
    listBuffer += temp
    println(listBuffer)

//    test()
  }

  def test(): Unit ={
    val tauL = 30
    val inputFile = "test_data/bh3_test2.txt"
    // 取得 RDD
    val coups = readFile(inputFile)
    regexText(coups)
    //val split = """([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4})|(\d+\w+)|(\d+)|(\w+)|([\u4E00-\u9FFF])""".r
//    var words = coups.flatMap(S=>{
//      (split findAllIn S).toList
//    }).persist(StorageLevel.MEMORY_AND_DISK_SER_2)
//
//    println("---------------")
//    words.foreach(S=>{
//      println(S)
//    })
//
//    coups.filter(_.length > 1).flatMap { text =>
//      val permutations = ListBuffer[String]()
//      for (i <- 1 to tauL) {// to 包含 tauL
//        for (j <- 0 until text.length) {// until 不包含 text.length
//          if (j + i <= text.length) {
//            // 增加文字
//            permutations += text.substring(j, j + i)
//          }
//        }
//      }
//      permutations
//    }.foreach(S=>{
//      println(S)
//    })

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
   *
   * @param corpus
   */
  def regexText(corpus: RDD[String]): Unit ={
    println("進入 test")
    corpus.foreach(text => {
      println("---------------------------------")
      val num = """([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4})|(\d+\w+)|(\d+)|(\w+)|([\u4E00-\u9FFF])""".r
      val all = (num findAllIn text).toList
      println(all)
      println("MIDDLE---------------------------------")

      var seg = text.split("[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}|\\pP|\\pS|\\s|　")
      for(i <- 0 to seg.length-1) {
        println(seg(i))
      }
      println("---------------------------------")
    })
    //println(corpus)
    println("離開 test")
    "鍾嘉豪，TEST，這個世界真的非常美麗"
//    val inputFile = "test_data/bh3_test.txt"
//
//    T.split("\\pP|\\pS|\\s|　").map(_.trim)
//    val num1 = """([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4})""".r
//    var text = "鍾嘉豪 16541a123 你好 aa 55 b195fea@gmail.com".split("\\PP")
//    for(i <- 0 to text.length-1) {
//      println(text(i))
//    }
//
//    val all1 = (num1 findAllIn "鍾嘉豪 16541a123 你好 aa 55 b195fea@gmail.com").toList
//    println(all1)
//    val num = """([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4})|(\d+\w+)|(\d+)|(\w+)|([\u4E00-\u9FFF])""".r
//    val all = (num findAllIn "鍾嘉豪 16541a123 ==\" 你好 aa 55 b195fea@gmail.com").toList
//    println(all)
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
    //    var text2 = "a-b-c".split("-", 2)    //    println(text2)
    //    for(i <- 0 to text2.length-1) {
    //      println(text2(i))
    //    }
  }
}