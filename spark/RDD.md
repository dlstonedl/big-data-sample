# RDD
弹性分布式数据集(Resilient Distributed Datasets)，RDD不存储真正要计算的数据；            
对RDD操作，会在Driver端转换成Task，下发到Executor计算分散在多台机器上的数据；          
RDD是一个抽象，逻辑上的概念；RDD记录的是元数据信息，而不是具体的数据；                

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

## cache 
数据缓存在Executor机器内存中；   
多次触发Action，才需要cache(cache)；   
缓存中的数据可以手动释放到(unpersist)；   
如果数据量太大，cache只会缓存一部分数据至内存，其他数据不缓存； 
建议先将数据过滤，缩小范围，然后再将数据缓存； 
cache不会生成新的RDD，只会标记RDD为cache，action的时候会触发；
cache底层是persist方法，默认存储级别为MEMORY_ONLY；

## persist
将HDFS中的数据缓存在executor端，可以指定缓存到**磁盘**或**内存**或**磁盘+内存**，可以指定缓存的份数(不同的机器)，可以指定是否序列化；    
OFF_HEAP: 堆外内存， 可以使用Alluxio，分布式内存存储系统

## checkpoint
1. 迭代计算，要求保证数据安全
2. 对速度要求不高(跟cache到内存对比)
3. 将中间结果保存到hdfs
checkout不会生成新的RDD，只会进行标记，action的时候会触发；
需要设置checkout的目录，一般是hdfs目录；
cache优先于checkpoint

## 广播变量
由Driver端生成，并广播到对应的Task；
广播变量广播后，就不能改变；如果需要改变，可以从分布式内存系统中读取，如redis；
广播变量可以从hdfs文件中生成，然后收集到Driver端，再进行广播；

## JDBC RDD
spark可以从多个数据源读取数据，本身有JdbcRDD,MongoRDD等；
问题：[1,5)，2个分区，   
id >= 1 AND id < 2    
id >= 3 AND id < 5    
导致记录2丢失；

## spark总体流程
RDD -> DAG -> Task -> Executor
1. 构建DAG(调用RDD上的方法，Driver)
2. DAGScheduler将DAG切分stage，将stage生成的Task以TaskSet的形式给TaskScheduler（切分的依据是shuffle，Driver）
3. TaskScheduler调度Task（根据资源情况将Task调度到相应的Executor中，涉及序列化，Driver）
4. Executor接收Task，然后将Task放入线程池中执行（Executor）

## DAG
有向无环图，数据执行过程，有方向，无闭环
DAG描述多个**RDD的转换过程**，任务执行时，可以按照DAG的描述执行真正的计算（数据被操作的一个过程）
DAG有边界，开始（sparkContext创建RDD），结束（触发action，调用run job）；
一个spark中有1到多个DAG，取决于触发action次数；
一个RDD只是描述一个环节，DAG由一至多个RDD组成，描述数据的整个过程；
一个DAG可能产生不同类型和功能的Task，会有不同的阶段；
输出目录不能存在，否则报错；

## stage
切分stage：一个复杂的业务逻辑（将多台机器上的数据聚合到一台机器上：shuffle），如果有shuffle，则存在数据强依赖；
同一个stage，会有多个算子，合并在一起，成为pipeline（流水线，有严格的顺序）

## RDD依赖
宽依赖：父RDD分区数据，给子RDD的多个分区（存在可能）
窄依赖：map，filter，union等，父RDD分区数据，给子RDD的一个分区，独生子女

shuffle：洗牌，将数据打散，父RDD一个分区数据，给子RDD的多个分区
shuffle会有网络传输，有网络传输，并不意味着有shuffle

RDD触发Action后，会根据这个RDD，从后往前推断依赖关系，遇到shuffle就切分stage，
递归切分，直至没有父RDD
切分完后，先提交前面的stage，执行完后提交后面的stage，stage会生成Task
一个stage会生成很多业务逻辑相同的Task，网络传输时，设计序列化及反序列化

## 共享变量
1. 广播变量
2. 累加器

## 运行模式
1. 本地模式，local
2. 集群模式，spark-submit 

## wordCount
HadoopRDD(K,V) -> MapPartitionsRDD(String) -> 
MapPartitionsRDD(String) -> MapPartitionsRDD(String, Int) -> 
shuffleRDD(String, Int) -> MapPartitionsRDD(NullWritable, text)    
每个分区生成2个Task：shuffleMapTask(读数据，进行计算，写入本地磁盘)，resultTask(拉取数据，进行计算，写入HDFS)；   
一共2个stage(阶段)，每个stage一个Task；     
shuffleMapTask数据，一定会写入本地磁盘，防止数据丢失； 


shuffle: spark re-distributing 数据的机制，是一种复杂而昂贵的操作，往上游拉取数据，groupBy,需要重新shuffle

2.0之前，RDD是spark主要的编程接口，2.0以后，Dataset取代了RDD


     
