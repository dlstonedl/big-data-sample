package com.dlstone.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class JavaWordCount {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf()
                .setAppName("JavaWordCount")
                .setMaster("local[*]");

        JavaSparkContext javaSparkContext = new JavaSparkContext(conf);

        JavaRDD<String> lines = javaSparkContext.textFile("hdfs://localhost:9000/wc");

        JavaRDD<String> words = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator());

        JavaPairRDD<String, Integer> wordAndOne = words.mapToPair(word -> new Tuple2<>(word, 1));

        JavaPairRDD<String, Integer> reduced = wordAndOne.reduceByKey((m, n) -> m + n);

        JavaPairRDD<Integer, String> swaped = reduced.mapToPair(tp -> tp.swap());

        JavaPairRDD<Integer, String> sorted = swaped.sortByKey(false);

        JavaPairRDD<String, Integer> result = sorted.mapToPair(tp -> tp.swap());

        result.saveAsTextFile("hdfs://localhost:9000/out");

        javaSparkContext.stop();
    }
}
