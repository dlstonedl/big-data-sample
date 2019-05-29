package com.dlstone.spark.DStream

import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import kafka.utils.{ZKGroupTopicDirs, ZkUtils}
import org.I0Itec.zkclient.ZkClient
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.{Duration, StreamingContext}

object KafkaDirectWordCount {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .setAppName("KafkaDirectWordCount")
      .setMaster("local[*]")
    val context = new StreamingContext(conf, Duration(5000))

    val group = "kafka-consumer-group"
    val topic = "test"
    val brokerList = "localhost:9092"
    val zkList = "localhost:2181"
    val topics: Set[String] = Set(topic)

    val topicDirs = new ZKGroupTopicDirs(group, topic)
    //获取zk中的路径，"/kafka-consumer-group/offsets/kafka-topic/"
    val zkTopicPath = s"${topicDirs.consumerOffsetDir}"

    val kafkaParams = Map(
      "metadata.broker.list" -> brokerList,
      "group.id" -> group,
      //从头开始读数据
      "auto.offset.reset" -> kafka.api.OffsetRequest.SmallestTimeString
    )

    val zkClient = new ZkClient(zkList)

    val children = zkClient.countChildren(zkTopicPath)

    var kafkaStream: InputDStream[(String, String)] = null
    var fromOffsets: Map[TopicAndPartition, Long] = Map()

    if (children > 0 ) {
      for (i <- 0 until children) {
        val partitionOffset = zkClient.readData[String](s"$zkTopicPath/${i}")
        val tp = TopicAndPartition(topic, i)
        fromOffsets += (tp -> partitionOffset.toLong)
      }

      val messageHandler = (mmd: MessageAndMetadata[String, String]) => (mmd.key(), mmd.message())
      kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, (String, String)](context, kafkaParams, fromOffsets, messageHandler)
    } else {
      kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](context, kafkaParams, topics)
    }

    var offsetRanges = Array[OffsetRange]()
    kafkaStream.foreachRDD(kafkaRDD => {
      offsetRanges = kafkaRDD.asInstanceOf[HasOffsetRanges].offsetRanges
      val lines = kafkaRDD.map(_._2)

      lines.foreachPartition(partition => {
        partition.foreach(x => {
          println(x)
        })
      })

      for (o <- offsetRanges) {
        val zkPath = s"${topicDirs.consumerOffsetDir}/${o.partition}"
        ZkUtils.updatePersistentPath(zkClient, zkPath, o.untilOffset.toString)
      }
    })

    context.start()
    context.awaitTermination()
  }

}
