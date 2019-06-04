package com.dlstone.spark.DStream

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Milliseconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object DStreamWorldCount {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("DStreamWorldCount").setMaster("local[*]")

    val context = new SparkContext(conf)

    //StreamingContext是对SparkContext的封装，增加了实时的功能
    //第二个参数是最下批次产生的时间间隔
    val streamingContext = new StreamingContext(context, Milliseconds(5000))

    //NetCat, nc -l -p 9999
    val lines: ReceiverInputDStream[String] = streamingContext.socketTextStream("localhost", 9999)

    val words: DStream[String] = lines.flatMap(_.split(" "))

    val wordAndOne: DStream[(String, Int)] = words.map((_, 1))

    val reduced: DStream[(String, Int)] = wordAndOne.reduceByKey(_+_)

    reduced.print()

    //启动spark streaming程序
    streamingContext.start()

    //等待优雅退出
    streamingContext.awaitTermination()

  }


}
