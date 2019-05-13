## Hive On MapReduce VS SparkSQL VS Hive On Spark

Ad-hoc查询：即席查询，数据仓库领域概念；  
用户根据自己的需求，灵活的选择查询条件，系统能够根据用户的选择生成相应的统计报表；
Presto(普雷斯托): 专门为大数据实时查询计算而设计和开发的产品；美团； 
Facebook开源；语言：Java；分布式数据查询框架； 

OLAP分析：On-line Analytical Processing，联机分析处理；
Apache kylin(麒麟): 开源的分布式分析引擎；滴滴，美团，58；

大数据查询：Presto,Kylin,Druid(实时),Impala

Phoenix(菲尼克斯,HBase的SQL驱动) + HBase； 
Phoneix: 使用于频繁写但读取少的事务型场景，OLTP，联机事务处理 

Alluxio: 开源的基于内存的分布式存储系统

