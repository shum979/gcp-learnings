package me.shubham

import java.nio.file.{Files, Paths}

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkGoogleCloudStorageWordCount {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
    sparkConf.set("spark.hadoop.google.cloud.auth.service.account.enable","true")

    if(args.size > 0){
     val keyPath = args(0)
      require(Files.exists(Paths.get(keyPath)),s"No file exists at - $keyPath")
      println(s"Using GCP credential file - $keyPath")
      sparkConf.set("spark.hadoop.google.cloud.auth.service.account.json.keyfile",keyPath)
    }

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .config(sparkConf)
      .appName("SimpleGCPExample")
      .getOrCreate()

    val data = spark.read.text("gs://recon-spark-test-bucket-1/source-data")

    val counts = data.rdd.map(_.getString(0))
      .flatMap(_.split(","))
      .map(word => (word,1))
      .reduceByKey(_+_)

//    counts.foreach(x => println(x))

    counts.saveAsTextFile("gs://recon-spark-test-bucket-1/new-output-data")
    println("Job completed successfully, exiting now ...")
    System.exit(0)
  }

}
