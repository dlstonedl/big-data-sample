import org.apache.spark.{SparkConf, SparkContext}

object ScalaWorldCount {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("ScalaWorldCount").setMaster("local[*]")

    val sc = new SparkContext(conf)

    val lines = sc.textFile("hdfs://localhost:9000/wc")

    val words = lines.flatMap(_.split(" "))

    val wordAndOne = words.map((_, 1))

    val reduced = wordAndOne.reduceByKey(_+_)

    val sorted = reduced.sortBy(_._2, false)

    val array = sorted.collect()

    println(array.toBuffer)

    sc.stop()
  }

}
