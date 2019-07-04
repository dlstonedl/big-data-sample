package com.dlstone.spark.DataFrame.join

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

object UdfDemo {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("UdfDemo")
      .master("local[*]")
      .getOrCreate()

    val nationDS: Dataset[String] = spark.read.textFile("src/main/scala/com/dlstone/spark/DataFrame/join/nationMap.txt")

    import spark.implicits._
    val nationMapDS: Dataset[(String, String)] = nationDS.map(line => {
      val fields = line.split("->")
      val eName = fields(0)
      val cName = fields(1)
      (eName, cName)
    })
    val nationMapInDriver = nationMapDS.collect()
    //广播(必须使用sparkContext)
    val broadCastRef: Broadcast[Array[(String, String)]] = spark.sparkContext.broadcast(nationMapInDriver)

    val personDS: Dataset[String] = spark.read.textFile("src/main/scala/com/dlstone/spark/DataFrame/join/person.txt")
    val personDF: DataFrame = personDS.map(line => {
      val fields = line.split(",")
      val userName = fields(0)
      val nation = fields(1)
      (userName, nation)
    }).toDF("user_name", "nation")
    personDF.createTempView("v_person")

    //定义一个自定义(UDF),并注册
    spark.udf.register("nation2CName", (nation: String) => {
      //获取广播变量
      val nationsInExecutor: Array[(String, String)] = broadCastRef.value
      val cName: String = nationsInExecutor
        .find(nationMap => nationMap._1.equals(nation))
        .map(nationMap => nationMap._2)
        .getOrElse("unKnow")
       cName
    })

    val result = spark.sql("SELECT nation2CName(nation) province, count(*) counts FROM v_person group by province order by counts DESC")
    result.show()
    spark.stop()
  }

}
