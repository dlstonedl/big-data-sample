# KAFKA
## 基本概念

broker: 安装kafka的服务器，需要绑定zk
producer: push，推送消息给broker，需要绑定topic
consumer: pull，主动从broker拉取消息，低版本需要绑定zk
consumer group: 同一个group中的consumer不会重复消费
topic: 消息分类