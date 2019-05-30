# KAFKA

kafka有很高的吞吐量，每秒百万

## 基本概念

broker: 安装kafka的服务器，无主从
producer: push，推送消息给broker，需要绑定topic，无需指定zk，需指定broker
consumer: pull，主动从broker拉取消息，低版本需要绑定zk
consumer group: 同一个group中的consumer不会重复消费，由消费者指定
topic: 主题，消息分类
replication: 副本数小于等于broker数，副本有主从
partition: 分区，物理分区
zookeeper: 保存broker信息，topic信息，consumer信息

broker存放消息以消息到达的顺序存放    
分区不是切分文件，而是消息到某个分区中去，每个分区存放部分消息    
leader失败时，新leader的选举，通过isr进行，第一个注册的follower自动成为leader

## 副本模式

### 同步复制：(默认)
1. producer从zk获取leader
2. producer向leader发消息
3. leader收到消息写本地log
4. follower从leader pull消息
5. follower写本地log，并向leader发送ack
6. leader收到所有follower的ack后，向producer发送ack
consumer端，从leader拉取消息

### 异步复制：
和同步复制的区别：leader写入log后，直接向producer发送ack，不需要等待follower的ack消息
缺点：broker错误时，follower可能没有同步

## kafka配置文件
```
server.properties

broker.id=1
delete.topic.enable=true
listeners=PLAINTEXT://node-1:9092
log.dirs=/data/kafka
zookeeper.connect=node-1:2181,node-2:2181,node-3:2181
```

## 启动kafka
```
${kafka-path}/bin/kafka-server-start.sh -daemon ${kafka-path}/config/server.properties

local start:
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
```

## 停止kafka
```
${kafka-path}/bin/kafka-server-stop.sh
```

## 创建topic
```
${kafka-path}/bin/kafka-topics.sh --create --zookeeper node-1:2181,node-2:2181,node-3:2181 --replication-factor 3 --partitions 3 --topic my-topic

/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test
```

## 列出所有topic
```
${kafka-path}/bin/kafka-topics.sh --list --zookeeper node-1:2181,node-2:2181,node-3:2181

/kafka-topics.sh --list --bootstrap-server localhost:9092

```

## 查看topic信息
```
${kafka-path}/bin/kafka-topics.sh --describe --zookeeper node-1:2181,node-2:2181,node-3:2181 --topic my-topic

/kafka-topics.sh --describe --bootstrap-server localhost:9092 --topic test
```

## 启动命令行生产者
```
${kafka-path}/bin/kafka-console-producer.sh --broker-list node-1:9092,node-1:9092,node-3:9092 --topic my-topic

/kafka-console-producer.sh --broker-list localhost:9092 --topic test

```

## 启动命令行消费者
```
${kafka-path}/bin/kafka-console-consumer.sh --zookeeper node-1:2181,node-2:2181,node-3:2181 --topic my-topic --from-beginning

/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning
```