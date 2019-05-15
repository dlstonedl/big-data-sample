# RDD
弹性分布式数据集，RDD不存储真正要计算的数据；            
对RDD操作，会在Driver端转换成Task，下发到Executor计算分散在多台机器上的数据；          
RDD是一个抽象，逻辑上的概念；             

RDD是一个代理，对代理进行操作会生成Task进行计算，操作代理就像操作一个本地集合一样，
不用关心任务调度，容错等；  

RDD操作分为两种：Transformation(lazy)和Action(触发任务立即执行)

RDD生成的三种方式：
1. 从分布式文件系统中创建；
2. 从Driver的scala集合中创建；
3. 转换已存在的Transformation创建；     


     