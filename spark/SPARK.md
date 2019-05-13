# SPARK

spark,内存和磁盘的计算框架，比MapReduce计算框架更高效  
MR需要将中间计算结果写文件，spark可以直接利用内存
DAG,Spark高效的原因，合并计算算子到一个stage  

一站式解决方案：Spark SQL + Spark Streaming + MLib + GraphX 

## 语言
Scala，Java，python，R  

## 部署
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

SparkSubmit



