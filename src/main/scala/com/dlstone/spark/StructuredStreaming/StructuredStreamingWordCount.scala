package com.dlstone.spark.StructuredStreaming

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

object StructuredStreamingWordCount {

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder()
      .appName("StructuredStreamingWordCount")
      .master("local[*]")
      .getOrCreate()

    import sparkSession.implicits._

    //NetCat, nc -l -p 9999
    val lines: DataFrame = sparkSession.readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", 9999)
      .load()

    val words: Dataset[String] = lines.as[String].flatMap(_.split(" "))

    val wordCounts: DataFrame = words.groupBy("value").count()

    val query = wordCounts.writeStream
      .outputMode("complete")
      .format("console")
      .start()

    query.awaitTermination()
  }

}
