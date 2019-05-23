# 实时计算

## 实时计算技术
Storm/JStorm|SparkStreaming|Flink  
:----------:|:------------:|:------------:  
实时性高|有延迟|实时性高
吞吐量较低|吞吐量高|吞吐量高
算子比较少|算子丰富|算子丰富
只能实时计算|离线+实时|离线+实时
没有|机器学习|没有
没有|图计算|没有
使用比较少|非常火|一般

## DStream
Spark Streaming提供的高层次的抽象
DStream: discretized stream, 需要StreamingContext支持（StreamingContext是对SparkContext增强）
DStream有两个任务：receiver，calculator
DStream最少需要两个线程（receiver，calculator），线程数 > receiver的数量
从文件系统中读取，不需要启动receiver

DStream可以看成是RDD的工厂，用来生成功能相同的RDD，只是操作数据不同；
DStream中有一个Map来保存RDD，key为时间戳；
对DStream的操作，其实就是对底层RDD的操作； 

## SparkStreaming连接Kafka方式
Receiver方式：Receiver接收固定时间间隔的数据，然后进行处理，效率低且容易丢失数据；使用Kafka高级API；自动维护偏移量；
Direct直连方式：相当于直接连接到Kafka的分区上，使用Kafka的底层API；效率高，需要自己维护偏移量；迭代器

## updateStateByKey
require the checkpoint directory to be configured

## transform
applying a RDD-to-RDD function to every RDD of the source DStream

## window
two parameters: windowLength and slideInterval.

Flume（监听文件变化,主动推送）
Kafka 消息中间件（临时存储数据，默认数据保存一周，高吞吐，高可用，多副本，整合实时计算）
Spark Streaming（主动按需拉取，数据不丢，不重复消费）
聚合后的数据存储：MySQL
明细数据，如打标签：Hbase
