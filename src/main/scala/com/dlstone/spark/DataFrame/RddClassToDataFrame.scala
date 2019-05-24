package com.dlstone.spark.DataFrame

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * RDD to DataFrame through class
  */
object RddClassToDataFrame {

  def main(args: Array[String]): Unit = {

    val sparkSession = SparkSession.builder()
      .appName("RddClassToDataFrame")
      .master("local[*]")
      .getOrCreate()

    val lines: RDD[String] = sparkSession.sparkContext.textFile("hdfs://localhost:9000/dataframe")

    val personRDD: RDD[Person] = lines.map(line => {
      val fields = line.split(",")
      Person(fields(0).toLong, fields(1), fields(2).toInt)
    })

    import sparkSession.implicits._
    val dataFrame: DataFrame = personRDD.toDF()

    dataFrame.createGlobalTempView("person")

    //Global temporary view is tied to a system preserved database `global_temp`
    val result: DataFrame = sparkSession.sql("select * from global_temp.person")

    result.show()
  }

}

case class Person(id: Long, name: String, age: Int)
