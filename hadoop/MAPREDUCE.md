# MapReduce

## MapTask
根据文件大小来拆分MapTask，每个MapTask处理一部分文件；    
MapTask中的map方法: 输入：文件中的一行；输出：KV键值对；
MapTask的个数可以很多，由调度器调度依次分批执行；  

## ReduceTask
根据MapTask的返回，聚合得到最终结果；   
ReduceTask中的reduce方法：输入：key和key的value集合；输出：KV键值对；   
ReduceTask的个数与MapTask没有直接联系；  

## shuffle
洗牌：根据MapTask的结果，将相同的Key分发到一个ReduceTask中；

## 序列化
MR中的数据需要网络传输，需要序列化及反序列化；  
Java本身的序列化机制，效率太差；  
Hadoop自身实现了自己的一套序列化机制，且为Java的基本数据类型提供了对应的封装类；  

