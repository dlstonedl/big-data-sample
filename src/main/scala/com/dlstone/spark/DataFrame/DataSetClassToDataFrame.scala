package com.dlstone.spark.DataFrame

import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
  * DataSet to DataFrame through class
  */
object DataSetClassToDataFrame {

  def main(args: Array[String]): Unit = {

    val sparkSession = SparkSession.builder()
      .appName("DataSetToDataFrame")
      .master("local[*]")
      .getOrCreate()

    val lines: Dataset[String] = sparkSession.read.textFile("hdfs://localhost:9000/dataframe")

    import sparkSession.implicits._
    val personDS: Dataset[Person] = lines.map(line => {
      val fields = line.split(",")
      Person(fields(0).toLong, fields(1), fields(2).toInt)
    })

    val result: DataFrame = personDS.select("id", "name", "age")
    result.show()
  }
}
