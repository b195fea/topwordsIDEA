package idv.zjh.topwords

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.log4j.Logger
import org.apache.spark.sql.SparkSession


object RunTestTopWORDs {
  @transient private[this] val LOGGER = Logger.getLogger(this.getClass.toString)

  def main(args: Array[String]): Unit = {
    // setup spark session
    LOGGER.info("開始運行")
    val spark = SparkSession.builder().master("local[1]").appName(this.getClass.toString).getOrCreate()
//    val fileName = "test1"
    val fileName = "baha_news"
    val inputFile = "test_data/"+ fileName +".txt"
    val outputFile = "test_data/output_update/" + fileName

    val files = FileSystem.get(spark.sparkContext.hadoopConfiguration)
    if (files.exists(new Path(outputFile))) files.delete(new Path(outputFile), true)
    LOGGER.info("讀取檔案開始")
    val corpus = spark.sparkContext.textFile(inputFile)
    LOGGER.info("讀取檔案結束")

    new TopWORDS(
      tauL = 10,
      tauF = 3,
      textLenThld = 2000,
      useProbThld = 1E-8,
      numIterations = 3,
      convergeTol = 1E-3,
      wordBoundaryThld = 0.0)
      .run(corpus, outputFile + "/dictionary", outputFile + "/segmented_texts")

    println("End ")
  }
}