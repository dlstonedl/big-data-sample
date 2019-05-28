## Hive On MapReduce VS SparkSQL VS Hive On Spark

## 技术栈
数据总线：Kafka  
日志收集：Logstash(可能存在丢失), Flume(保证不丢失)    
分布式计算框架：Storm, Hadoop, Spark, Flink  
分布式文件系统：HDFS  
分布式资源管理框架：YARN, Mesos  
分布式数据存储：Cassandra, HBase
可视化：Kibana  
工具层：Kylin  

## 大数据平台需要完成的功能
1. 数据存储层(持久化)；
2. 数据获取层；
3. 数据持久化层；
4. 数据展示层；

数据采集: HDFS API; Flume; Logstash; Filebeat; Sqoop; Canal; DataX;     
数据存储: HDFS;   
数据离线计算: MapReduce; Hive(SQL On Hadoop); SparkSQL;    
数据实时计算: Storm; Spark Streaming;    
采集数据传输，多次消费: Kafka;   
任务调度监控系统: Oozie;   
实时查询存储: HBase(Kylin + Phoenix); Redis; MongoDB; ElasticSearch; Cassandra;   

## 大数据发展方向
1. 运维；
2. 架构；
3. 统计分析；

https://www.slideshare.net/Khalid-Imran/big-data-technology-stack-nutshell   
https://blog.panoply.io/the-big-data-stack-powering-data-lakes-data-warehouses-and-beyond


## Ad-hoc查询：即席查询，数据仓库领域概念；  
用户根据自己的需求，灵活的选择查询条件，系统能够根据用户的选择生成相应的统计报表；
Presto(普雷斯托): 专门为大数据实时查询计算而设计和开发的产品；美团； 
Facebook开源；语言：Java；分布式数据查询框架； 

OLAP分析：On-line Analytical Processing，联机分析处理；
Apache kylin(麒麟): 开源的分布式分析引擎；滴滴，美团，58；

大数据查询：Presto,Kylin,Druid(实时),Impala

Phoenix(菲尼克斯,HBase的SQL驱动) + HBase； 
Phoneix: 使用于频繁写但读取少的事务型场景，OLTP，联机事务处理 

Alluxio: 开源的基于内存的分布式存储系统

Hive+MapReduce  SparkSQL(Hive on Spark)
Storm/JStorm    Spark Streaming 

梳理spark中的技术，哪些更实用
Spark SQL(old), DataFrames, Datasets
Spark Streaming(old), Structured Streaming
    
## 资源
[大数据资源](https://zhuanlan.zhihu.com/p/26742893)  
[大数据技术栈](https://zhuanlan.zhihu.com/p/51973232)  

