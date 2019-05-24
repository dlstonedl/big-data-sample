package com.dlstone.spark.DataFrame

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types._
import org.apache.spark.sql._

/**
  * RDD to DataFrame through Row
  */
object RddRowToDataFrame {

  def main(args: Array[String]): Unit = {

    val sparkSession = SparkSession.builder()
      .appName("SqlRddRow")
      .master("local[*]")
      .getOrCreate()

    val lines: RDD[String] = sparkSession.sparkContext.textFile("hdfs://localhost:9000/dataframe")

    val rowRdd: RDD[Row] = lines.map(line => {
      val fields = line.split(",")
      val id = fields(0).toLong
      val name = fields(1)
      val age = fields(2).toInt
      Row(id, name, age)
    })

    val schema: StructType = StructType(List(
      StructField("id", LongType, true),
      StructField("name", StringType, true),
      StructField("age", IntegerType, true)
    ))

    val df: DataFrame = sparkSession.createDataFrame(rowRdd, schema)

    df.createTempView("person")

    val result: DataFrame = sparkSession.sql("select * from person")

    result.show()
  }

}
