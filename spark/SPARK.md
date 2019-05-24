# SPARK

spark,内存和磁盘的计算框架，比MapReduce计算框架更高效  
MR需要将中间计算结果写文件，spark可以直接利用内存
DAG,Spark高效的原因，合并计算算子(operation)到一个stage  

一站式解决方案：Spark SQL + Spark Streaming + MLib + GraphX 

## 语言
Scala，Java，python，R，SQL 

## 部署(standalone)
主从结构：Master，Worker     
spark可以单独安装，建议与Hadoop的DataNode安装在一起    
建议移动计算，而不是移动数据    

配置spark-env.sh文件
```
// JDK必须安装，scala可以不安装
export JAVA_HOME=
export SPARK_MASTER_HOST=localhost
export SPARK_MASTER_PORT=7077(默认端口)
./start-all.sh
```
配置slaves文件: 配置Worker地址或主机名

sbin目录不建议配置到PATH路径中，避免与Hadoop命令重复     

管控台(Netty)：http://localhost:8080/

## 解决单点故障
部署两个Master，引入ZooKeeper，分布式协调服务   

## ZooKeeper
1. 选举
2. 保存活跃Master的信息
3. 保存所有worker的资源信息和资源使用情况(为了故障切换)

## 执行流程
SparkSubmit(Driver): 上传Jar包并调度Executor 
SparkSubmit -> 请求Master -> Master通知Worker创建Executor -> Executor自动连接SparkSubmit

CoarseGrainedExecutorBackend(Executor): 具体执行计算逻辑  
SparkSubmit分发Task -> Executor

```
spark-submit --master spark://localhost:7077 --class ${main-class-path} 
${executor-memory} ${executor-cores} ${jar-path} ${jar-params}

./spark-submit --master spark://localhost:7077 --class org.apache.spark.examples.SparkPi \
--executor-memory 1g --executor-cores 8  \
${spark-path}/examples/jars/spark-examples_2.11-2.3.3.jar 50
```

## YARN与standalone对比
ResourceManager    Master       
NodeManager        Worker        
yarnChild          Executor     
client             SparkSubmit(client + applicationMaster)         
applicationMaster   

NodeManager管理yarnChild，applicationMaster   

## Spark Shell
spark-shell，使用scala语言，方便学习与开发，会自动创建一个SparkSubmit       
```
./spark-shell --master spark://localhost:7077 --executor-memory 5g --total-executor-cores 12

// 内存 5G，CPU 12个

sc.textFile("hdfs//localhost:9000/wc")
.flatMap(_.split(" "))
.map((_, 1))
.reduceByKey(_+_)
.collect
```     
如果没有--master，则启动的是spark的本地模式，模拟spark集群    

## spark本地调试
```
setMaster("localhost[4]") //本地开线程启动，例如：4个线程
```     

spark需要分布式文件系统：HDFS，S3等






