package com.dlstone.spark.DataFrame.join

import org.apache.spark.sql.{DataFrame, SparkSession}

object JoinDemo {

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder()
      .appName("JoinDemo")
      .master("local[*]")
      .getOrCreate()

//    sparkSession.conf.set("spark.sql.autoBroadcastJoinThreshold", -1)

    import sparkSession.implicits._
    val PersonFrame: DataFrame = Seq(
      ("LaoLi", "China"),
      ("Jack", "USA"),
      ("MingRen", "Japan")
    ).toDF("name", "nation")

    val NationFrame: DataFrame = Seq(
      ("China", "中国"),
      ("USA", "美国"),
      ("Japan", "日本")
    ).toDF("eName", "cName")

    val result: DataFrame = PersonFrame.join(NationFrame, $"nation" === $"eName")

    result.explain()

    result.show()

    sparkSession.stop()

  }
}
