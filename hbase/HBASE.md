# HBase
HBase是一个NoSql数据库，可以提供数据的实时随机读写

## 基本特性
+ 数据的最终持久化存储是基于HDFS -> 存储容量可以随时在线扩容
+ HBase的数据增删改查功能模块：分布式系统 -> HBase是一个分布式数据库系统

## 表结构
有行的概念，但没有字段的概念   
没有固定的字段定义，每行存储的都是KV对  
列族：可以指定将哪些KV插入哪个列族，物理上存储，按照列族来分割  
表名：行键(rowkey,不能重复)    
时间戳     
KV键值对(cell)      
数据类型：只支持byte[]    
对事务的支持很差    

## 基本原理
Region Server: 管理一个或多个Region，负责数据的增删改查     
Region: 包含行键的一个区间，整张表由于数据量过大，会被横向切分成若干个region(用rowkey范围标识)，
不同的region的数据存储在不同文件中   

HBase会对插入的数据按顺序存储(按字典排序):
+ 首先会按行键排序
+ 同一行里面的kv会按列族排序，再按k排序   
这个特性会影响查询效率，可以将查询条件拼到rowKey中

Master: 负责监管Region Server     
HDFS: /hbase/库目录/表目录/region0/列族1/文件，不同列族的数据会存储在不同文件中     
HA: 高可用，zookeeper,master可以有多个，一个active，一个standby   
MemStore: 存储热数据   
log文件  

## 客户端读取数据流程
1. 去zk上查找meta表所在region server
2. 去meta表查找数据所在的region信息(meta表：所有表的region信息)
3. 去目标region server查找所需信息

## 高级特性
1. 批量数据快速导入HBase -- BulkLoad
2. HBase性能优化
3. MapReduce分析HBase中数据
4. HBase数据建索引 -- 过滤查询；二级索引（协处理器，Solr，ES）






