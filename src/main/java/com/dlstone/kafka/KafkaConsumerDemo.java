package com.dlstone.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.*;

public class KafkaConsumerDemo {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("zookeeper.connect", "localhost:2181");
        props.put("group.id", "consumer-demo");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("auto.offset.reset", "smallest");

        ConsumerConfig config = new ConsumerConfig(props);
        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(config);
        Map<String, Integer> topicMap = new HashMap<>();
        topicMap.put("test", 1);
        Map<String, List<KafkaStream<byte[], byte[]>>> messages = consumer.createMessageStreams(topicMap);

        for (Map.Entry<String, List<KafkaStream<byte[], byte[]>>> entry: messages.entrySet()) {
            String key = entry.getKey();
            System.out.println("key = " + key);
            List<KafkaStream<byte[], byte[]>> values = entry.getValue();
            values.forEach(stream -> {
                ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
                while (iterator.hasNext()) {
                    MessageAndMetadata<byte[], byte[]> metadata = iterator.next();
                    String topic = metadata.topic();
                    byte[] messageKey = metadata.key();
                    long offset = metadata.offset();
                    int partition = metadata.partition();
                    byte[] message = metadata.message();
                    System.out.println("topic = " + topic + ", messageKey = " + new String(messageKey)
                            + ", message = " + new String(message) + ", offset = " + offset
                            + ", partition = " + partition);
                }
            });
        }
    }
}
