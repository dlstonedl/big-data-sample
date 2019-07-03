# SparkSQL 
SparkSQL是spark的高级模块，一个SQL解析引擎，将SQL转换成特殊的RDD（DataFrame）

SparkSQL用来处理结构化数据，支持两种编程API：SQL方式和DataSet API方式

SparkSQL兼容Hive（元数据库，SQL语法，UDF，序列化，反序列化）

SparkSQL支持多种数据源，提供标准的JDBC，ODBC连接

## DataFrame
DataSet 分布式数据集，是对RDD的进一步封装，是更加智能的RDD
DataSet只有一列，默认为value
DataFrame 组织成列的DataSet，类似关系型数据库的table，DataSet of Rows

DataFrame，结构化数据的元数据信息（表头，列名，列类型）
DataFrame = RDD + Schema

## 1.x 与 2.x API变化
1.x: 

SQL方式 
SparkContext -> SQLContext -> RDD -> Class -> DataFrame -> tempTable -> SQL(Transformation) -> Action   
SparkContext -> SQLContext -> RDD -> Row -> Schema -> DataFrame -> tempTable -> SQL(Transformation) -> Action

DataFrame方式   

2.x SparkSession

## sparkSQL流程
逻辑计划 -> 执行器优化 -> 物理计划 -> 集群运行

## 数据格式
jdbc：数据库
json：json格式，没有值则为null
csv：逗号隔开
Parquet：列式存储（spark默认数据源）

## 三种join方式
1. Broadcast Join: 小表与大表Join（Hash Join，broadcast）
2. Shuffle Hash Join: 小表（数据量比内存大）与大表Join（Hash Join，shuffle）
3. Sort Merge Join: 两张较大表之间join（shuffle，sort, merge, 无需将表的数据加载至内存）

## 窗口函数
+ 排名函数：rank, dense_rank, percent_rank, ntile, row_number
+ 分析函数：cume_dist, lag, lead
+ 聚合函数：普通的聚合函数都可以作为窗口聚合函数使用
窗口函数计算的一组行，被称为Frame，每一个被处理的行都有一个唯一的Frame相关联

### 使用窗口函数
+ 在SQL语句中支持的函数中添加OVER语句，例如：row_number() over(...)
+ 使用DataFrame API在支持的函数调用over()方法，例如：rank().over(...)

### 窗口规范
分区规范：PARTITION BY
排序规范：ORDER BY
帧规范：ROW帧和RANGE帧