package com.dlstone.spark.DataFrame

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

object SQLWordCount {

  def main(args: Array[String]): Unit = {

    val sparkSession = SparkSession.builder()
      .appName("SQLWordCount")
      .master("local[*]")
      .getOrCreate()

    val lines: Dataset[String] = sparkSession.read.textFile("hdfs://localhost:9000/wc")

    import sparkSession.implicits._
    val words: Dataset[String] = lines.flatMap(_.split(" "))

    //注册视图
    words.createTempView("sql_wc")

    //执行SQL，(transformation)
    val result: DataFrame = sparkSession.sql("select value word, count(*) counts from sql_wc group by word order by counts desc")

    result.show()

    sparkSession.stop()
  }

}
