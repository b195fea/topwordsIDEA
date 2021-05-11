package idv.zjh.test

import org.mongodb.scala._

object mongodbTest {
  def main(args: Array[String]): Unit = {
    println("123")

    val uri: String = "mongodb://localhost:27017/"
//    System.setProperty("org.mongodb.async.type", "netty")
    val client: MongoClient = MongoClient(uri)
    val db: MongoDatabase = client.getDatabase("bh3_scrapy")
    var col = db.getCollection("bh3_2")
    println("456")

//    println(col.countDocuments().)
//    var data = col.find().explain().toString()
//    println(data)
    println("789")
//    col.find().foreach( T => {
//      print(T)
//    })
  }
}
