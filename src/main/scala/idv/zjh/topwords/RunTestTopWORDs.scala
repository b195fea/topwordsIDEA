package idv.zjh.topwords

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.sql.SparkSession


object RunTestTopWORDs {
    println("start ")
    def main(args: Array[String]) : Unit = {
    // setup spark session
    val spark = SparkSession.builder().master("local[1]").appName(this.getClass.toString).getOrCreate()
    val inputFile = "test_data/bh3_test.txt"
    val outputFile = "test_data/output/bh3_test"

    val files = FileSystem.get(spark.sparkContext.hadoopConfiguration)
    if (files.exists(new Path(outputFile))) files.delete(new Path(outputFile), true)
    val corpus = spark.sparkContext.textFile(inputFile)

    new TopWORDS(
      tauL = 30,
      tauF = 3,
      textLenThld = 2000,
      useProbThld = 1E-8,
      numIterations = 10,
      convergeTol = 1E-3,
      wordBoundaryThld = 0.0)
      .run(corpus, outputFile + "/dictionary", outputFile + "/segmented_texts")


      println("End ")
  }
}