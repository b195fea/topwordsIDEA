package idv.zjh.topwords

import idv.zjh.topwords.RunTestTopWORDs.run
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession


object RunTestTopWORDs3 {
  @transient private[this] val LOGGER = Logger.getLogger(this.getClass.toString)



  def run(fileName:String): Unit = {
    var inputFile = "test_data/"+ fileName +".txt"
    var outputFile = "test_data/output_update/" + fileName
    var spark = SparkSession.builder().master("local[1]").appName(fileName).getOrCreate()
    var files = FileSystem.get(spark.sparkContext.hadoopConfiguration)
    if (files.exists(new Path(outputFile))) files.delete(new Path(outputFile), true)
    LOGGER.info("讀取檔案開始")
    var corpus = spark.sparkContext.textFile(inputFile)
    LOGGER.info("讀取檔案結束")

    println("Start "+fileName)
    new TopWORDS(
      tauL = 8,
      tauF = 3,
      textLenThld = 2000,
      useProbThld = 1E-9,
      numIterations = 3,
      convergeTol = 1E-3,
      wordBoundaryThld = 0.0)
      .run(corpus, outputFile + "/dictionary", outputFile + "/segmented_texts")

    println("End "+fileName)
  }

  def main(args: Array[String]): Unit = {
    // setup spark session
    LOGGER.info("開始運行")
    for (i <- 49 to 72) {
      var text = "pre_subbsn11_20220324_" + i
      println(text)
      run(text)
    }

//        println("pre_subbsn15_20220324_362")
//        run("pre_subbsn15_20220324_362")
//    for (i <- 47 to 55) {
//      var text = "pre_subbsn15_20220324_" + i
//      println(text)
//      run(text)
//    }

//    run("col_pre_bahanews_normal_2021905_00")
//    run("col_pre_bahanews_normal_2010825_01")
//    run("col_pre_bahanews_normal_2010825_02")
//    run("col_pre_bahanews_normal_2010825_03")
//    run("col_pre_bahanews_normal_2010825_04")
//    run("col_pre_bahanews_normal_2010825_05")
//    run("col_pre_bahanews_normal_2010825_06")
//    run("col_pre_bahanews_normal_2010825_07")
//    run("col_pre_bahanews_normal_2010825_08")
//    run("col_pre_bahanews_normal_2010825_09")
  }
}