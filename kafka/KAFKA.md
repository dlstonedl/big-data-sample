# KAFKA
## 基本概念

broker: 安装kafka的服务器，
producer: push，推送消息给broker，需要绑定topic
consumer: pull，主动从broker拉取消息，低版本需要绑定zk
consumer group: 同一个group中的consumer不会重复消费
topic: 消息分类


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
```

## 停止kafka
```
${kafka-path}/bin/kafka-server-stop.sh
```

## 创建topic
```
${kafka-path}/bin/kafka-topics.sh --create --zookeeper node-1:2181,node-2:2181,node-3:2181 --replication-factor 3 --partitions 3 --topic my-topic
```

## 列出所有topic
```
${kafka-path}/bin/kafka-topics.sh --list --zookeeper node-1:2181,node-2:2181,node-3:2181
```

## 查看topic信息
```
${kafka-path}/bin/kafka-topics.sh --describe --zookeeper node-1:2181,node-2:2181,node-3:2181 --topic my-topic
```

## 启动命令行生产者
```
${kafka-path}/bin/kafka-console-producer.sh --broker-list node-1:9092,node-1:9092,node-3:9092 --topic my-topic
```

## 启动命令行消费者
```
${kafka-path}/bin/kafka-console-consumer.sh --zookeeper node-1:2181,node-2:2181,node-3:2181 --topic my-topic --from-beginning
```