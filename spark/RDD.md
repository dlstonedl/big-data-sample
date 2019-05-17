# RDD
弹性分布式数据集(Resilient Distributed Datasets)，RDD不存储真正要计算的数据；            
对RDD操作，会在Driver端转换成Task，下发到Executor计算分散在多台机器上的数据；          
RDD是一个抽象，逻辑上的概念；             

RDD是一个代理，对代理进行操作会生成Task进行计算，操作代理就像操作一个本地集合一样，
不用关心任务调度，容错等；  

RDD操作分为两种：Transformation(lazy)和Action(触发任务立即执行)

## 创建RDD
1. 从分布式文件系统中创建；
2. 从Driver端的集合中创建；
3. 转换已存在的Transformation创建；     

## RDD分区
分区数：默认为2，分区数等于最后输出文件个数；   
HDFS读取数据分区数：由总的文件长度和允许最小分区数量决定；    
一个分区对应一个Task，分区中并没有数据，而是记录要读取哪些数据，Task真正读取数据；    

## RDD算子
mapPartitionWithIndex 一次获取一个分区，并且可以将分区的编号取出来，可知道哪些分区中有哪些数据；   
aggregate 每个分区首先单独计算(函数)，然后将结果聚合(函数)；      
注意：分区返回结果的顺序不确定；初始值，分区计算会使用，整体聚合计算时也会使用；     
aggregateByKey  相同key才聚合，初始值只会在分区中使用； 


collect会将数据拉到Driver端，数据量大会压垮机器，存在瓶颈，应该直接在Executor中写入数据库
Driver遇到Action时，会从后往前推导所有的算子，并计算分区数；Task在Driver端生成，通过网络发送给Executor；    
Driver默认最大收集1G数据，超过1G则不处理；   

collect: Action
aggregate: Action
saveAsTextFile: Action
foreach: Action, Executor单条处理
foreachPartition: Action, Executor批量处理  

## RDD五大特征
1. 一系列分区，分区有编号，有顺序
2. 每个切片都有一个函数，用于对数据进行处理
3. RDD和RDD之间存在依赖关系
4. 可选，(k, v)类型的RDD，有一个分区器，默认是hash-partition
5. 可选，如果是从HDFS中读取数据，会得到数据的最优位置

shuffleMapTask
resultTask
stage(阶段)

shuffle: spark re-distributing 数据的机制，是一种复杂而昂贵的操作，往上游拉取数据，groupBy,需要重新shuffle

2.0之前，RDD是spark主要的编程接口，2.0以后，Dataset取代了RDD


     
