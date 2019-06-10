# HBase

## 基本特性
+ 数据的最终持久化存储是基于HDFS -> 存储容量可以随时在线扩容
+ HBase的数据增删改查功能模块：分布式系统 -> HBase是一个分布式数据库系统

## 表结构
表名；行键(rowkey,不能重复)；列族；时间戳；KV键值对(cell)
数据类型：只支持byte[]

## 基本原理
Region Server: 管理一个或多个Region，负责数据的增删改查   
Region: 包含行键的一个区间   

Master: 负责监管Region Server   

HDFS: /hbase/库目录/表目录/region0/列族1/文件，不同列族的数据会存储在不同文件中   

HA: 高可用，zookeeper,master可以有多个，一个active，一个standby

MemStore: 存储热数据

log文件

## 部署进程
管理角色：HMaster
数据节点角色：HRegionServer，应该与hdfs中的datanode安装在一起  

## 客户端读取数据流程
1. 去zk上查找meta表所在region server
2. 去meta表查找数据所在的region信息(meta表：所有表的region信息)
3. 去目标region server查找所需信息






