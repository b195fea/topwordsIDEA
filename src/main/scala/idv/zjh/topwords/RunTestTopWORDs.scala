package idv.zjh.topwords


import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession


object RunTestTopWORDs {
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
      tauL = 15,
      tauF = 2,
      textLenThld = 2000,
      useProbThld = 1E-8,
      numIterations = 2,
      convergeTol = 1E-3,
      wordBoundaryThld = 0.0)
      .run(corpus, outputFile + "/dictionary", outputFile + "/segmented_texts")

    println("End "+fileName)
  }

  def main(args: Array[String]): Unit = {
    // setup spark session
    LOGGER.info("開始運行")
//        run("pre_subbsn_covid_20220419_00")
    run("pre_subbsn_covid_20220509_00")
//    for (i <- 0 to 9) {
//      var text = "pre_subbsn11_20220324_0" + i
//      println(text)
//      run(text)
//    }
//
//    for (i <- 10 to 24) {
//      var text = "pre_subbsn11_20220324_" + i
//      println(text)
//      run(text)
//    }

//    println("pre_subbsn15_20220324_361")
//    run("pre_subbsn15_20220324_361")
//    println("pre_subbsn15_20220324_00")
//    run("pre_subbsn15_20220324_00")
//    println("pre_subbsn15_20220324_01")
//    run("pre_subbsn15_20220324_01")
//    println("pre_subbsn15_20220324_02")
//    run("pre_subbsn15_20220324_02")
//    println("pre_subbsn15_20220324_03")
//    run("pre_subbsn15_20220324_03")
//    println("pre_subbsn15_20220324_04")
//    run("pre_subbsn15_20220324_04")
//    println("pre_subbsn15_20220324_05")
//    run("pre_subbsn15_20220324_05")
//    println("pre_subbsn15_20220324_06")
//    run("pre_subbsn15_20220324_06")
//    println("pre_subbsn15_20220324_07")
//    run("pre_subbsn15_20220324_07")
//    println("pre_subbsn15_20220324_08")
//    run("pre_subbsn15_20220324_08")
//
//    println("pre_subbsn15_20220324_09")
//    run("pre_subbsn15_20220324_09")
//
//    for (i <- 10 to 24) {
//      var text = "pre_subbsn15_20220324_" + i
//      println(text)
//      run(text)
//    }
  }
}