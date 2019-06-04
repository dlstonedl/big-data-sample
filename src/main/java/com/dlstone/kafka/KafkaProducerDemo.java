package com.dlstone.kafka;

import kafka.producer.KeyedMessage;
import kafka.producer.Producer;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;
import scala.collection.JavaConversions;
import scala.collection.Seq;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class KafkaProducerDemo {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("metadata.broker.list", "localhost:9092");
        props.put("serializer.class", StringEncoder.class.getName());

        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<>(config);

        List<KeyedMessage<String, String>> messages = new ArrayList<>();
        KeyedMessage<String, String> msg = new KeyedMessage<>("test","hash-key" ,"hello kafka producer");
        messages.add(msg);

        Seq<KeyedMessage<String, String>> seq = JavaConversions.asScalaIterator(messages.iterator()).toSeq();
        producer.send(seq);
        System.out.println("send over!");
    }
}
