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
