分布式数据同步系统: DataBus

Partition:
1. 每一个Topic被切分为多个Partitions;
2. 消费者数目少于或等于Partition的数目;
3. 每一个Broker保存Topic的一个或多个Partitions;
4. Consumer Group中仅有一个Consumer读取Topic的一个或多个partitions，并且是唯一的consumer;

Producer Api
Consumer Api
Streams Api
Connectors Api

数据可持久化

kafka connect:
两个概念: Source, Sink

```
FileConnector-Demo:
数据来源: FileStreamSource
数据目的地: FileStreamSink
使用kafka默认的配置文件，演示kafka文件connect
官网的例子需要修改文件的路径，否则可能找不到kafka可能找不到文件所在

./connect-standalone.sh ../config/connect-standalone.properties ../config/connect-file-source.properties ../config/connect-file-sink.properties
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic connect-test --from-beginning

```

kafka日志管理：
删除日志：segment，删除旧的segment，适合不改变的数据
压缩日志：适合根据key值改变的数据

生产者：无状态
消费者：需要管理状态，提交offset

KStreams API:
1. 创建source node，从topic中拉取消息；
2. 在source node上添加processing node，即child node；
3. 创建sink node，将数据写入topic；

配置：
StreamsConfig.APPLICATION_ID_CONFIG
StreamsConfig.BOOTSTRAP_SERVERS_CONFIG

序列化和反序列化：kafka以二进制格式传输
Serdes默认实现：Strings, Byte arrays, Long, Integer, Double

无状态：
KStream.mapValues
KStream.branch 分割
KStream.foreach 无状态，将数据写入第三方数据库
KStream.selectKey

有状态:
KStream.process 有状态





jdbc: confluent


kafka stream: 拓扑结构
核心概念：
1. 事件时间和处理时间
2. 窗口支持
3. 简单而高效的管理和实时查询应用状态

流：无界，连续更新的数据集；有序，不可变，容错，可回放的数据流(数据：KV对)
流处理器(stream processor):
Source Processor: 没有上游处理器，从kafka的topic中读取数据，传递给下游处理器；
Sink Processor: 没有下游处理器，将上游处理器中的数据保存至kafka的topic中；
注意: 在普通的流处理器节点中，可以访问其他远程系统；

创建流处理拓扑结构:
1. kafka Stream DSL，常用的数据转换；
2. Processor API，低层级API，用户定制；

Time:
Event time: 事件或记录产生的时间；
Processing time: 事件或记录消费的时间；
Ingestion time: 时间或记录到达kafka的时间；

聚合操作:




