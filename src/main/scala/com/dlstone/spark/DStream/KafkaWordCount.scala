package com.dlstone.spark.DStream

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object KafkaWordCount {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("KafkaWordCount").setMaster("local[*]")

    val streamingContext = new StreamingContext(conf, Seconds(5))

    val zkQuorum = "host:port,host:post,host:post"
    val group = "kafka-group"
    val topic = Map[String, Int]("xiaoniu" -> 1)

    val data: ReceiverInputDStream[(String, String)] = KafkaUtils.createStream(streamingContext, zkQuorum, group, topic)

    val lines: DStream[String] = data.map(_._2)

    val words: DStream[String] = lines.flatMap(_.split(" "))

    val wordAndOne = words.map((_, 1))

    val reduced: DStream[(String, Int)] = wordAndOne.reduceByKey(_+_)

    reduced.print()

    streamingContext.start()
    streamingContext.awaitTermination()
  }

}
