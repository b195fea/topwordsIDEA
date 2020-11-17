package idv.zjh.topwords.test

import idv.zjh.topwords.Preprocessing
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel

case object TestTopWords {
  def main(args: Array[String]): Unit = {
    readCorpus()
  }

  /**
   * 讀取文檔
   */
  def readCorpus(): Unit = {
    val spark = SparkSession.builder().master("local[1]").appName(this.getClass.toString).getOrCreate()
    val inputFile = "test_data/bh3_test.txt"
    val corpus = spark.sparkContext.textFile(inputFile)
//    corpus.
  }
}
