package com.dlstone.spark.DataFrame.join

import java.lang

import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}

object UdafDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("UdafDemo")
      .master("local[*]")
      .getOrCreate()

    val range: Dataset[lang.Long] = spark.range(1, 11)

    val customGeoMean = new CustomGeoMean

    spark.udf.register("cgm", customGeoMean)
    range.createTempView("v_range")
    val result: DataFrame = spark.sql("SELECT cgm(id) result FROM v_range")

    result.show()

    spark.stop()
  }
}

class CustomGeoMean extends UserDefinedAggregateFunction {

  //输入数据类型
  override def inputSchema: StructType = StructType(List(
    StructField("item", LongType)
  ))

  //产生中间结果的数据类型
  override def bufferSchema: StructType = StructType(List(
    //乘积
    StructField("product", LongType),
    //参与运算数字个数
    StructField("counts", LongType)
  ))

  //最终返回结果类型
  override def dataType: DataType = DoubleType

  //确保一致性，一般用true
  override def deterministic: Boolean = true

  //指定初始值
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    //相乘product初始值
    buffer(0) = 1L
    //counts初始值
    buffer(1) = 0L
  }

  //更新中间结果，每条数据参与运算时调用(分区中调用)
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    //product乘积
    buffer(0) = buffer.getLong(0) * input.getLong(0)
    //counts
    buffer(1) = buffer.getLong(1) + 1L
  }

  //全局聚合
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    //product乘积
    buffer1(0) = buffer1.getLong(0) * buffer2.getLong(0)
    //counts
    buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
  }

  //最终结果
  override def evaluate(buffer: Row): Any = {
    math.pow(buffer.getLong(0), 1.toDouble / buffer.getLong(1))
  }
}