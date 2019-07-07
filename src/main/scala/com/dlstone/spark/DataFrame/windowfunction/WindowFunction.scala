package com.dlstone.spark.DataFrame.windowfunction

import org.apache.spark.sql.{Dataset, SparkSession}

object WindowFunction {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("WindowFunction")
      .master("local[*]")
      .getOrCreate()

    val dataSet: Dataset[String] = spark.read.textFile("src/main/scala/com/dlstone/spark/DataFrame/windowfunction/exam.txt")

    import spark.implicits._
    val examDF = dataSet.map(line => {
      val fields = line.split(",")
      val gradeId = fields(0).toInt
      val classId = fields(1).toInt
      val studentId = fields(2).toInt
      val score = fields(3).toDouble
      (gradeId, classId, studentId, score)
    }).toDF("gradeId", "classId", "studentId", "score")

    examDF.createTempView("v_exam")

    val result = spark.sql("SELECT gradeId, classId, studentId, score, row_number() over(partition by gradeId, classId order by score desc) as rank from v_exam")

    result.show()

    spark.stop()
  }
}
