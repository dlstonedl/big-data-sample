package com.dlstone.spark.DataFrame

import org.apache.spark.sql.{Dataset, SparkSession}

object DataSetWordCount {

  def main(args: Array[String]): Unit = {

    val sparkSession = SparkSession.builder()
      .appName("DataSetWordCount")
      .master("local[*]")
      .getOrCreate()

    //DataSet只有一列，默认这列叫value
    val lines: Dataset[String] = sparkSession.read.textFile("hdfs://localhost:9000/wc")

    //导入隐式转换
    import sparkSession.implicits._
    val words: Dataset[String] = lines.flatMap(_.split(" "))

    //导入聚合函数
    import org.apache.spark.sql.functions._
    val counts = words.groupBy($"value".as("word"))
      .agg(count("*") as "counts")
      .orderBy($"counts" desc)
    counts.show()

    //使用DataSet的API（DSL）
//    val len = words.groupBy($"value" as "word").count().count()
//    println(len)

    sparkSession.stop()
  }

}
