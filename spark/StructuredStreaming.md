# Structured Streaming

建立在Spark SQL engine之上的流处理引擎，流计算表达方式与静态数据上的批处理一样，
能使用DataSet/DataFrame API   
静态有界数据   
流式无界数据   

## 处理模式
+ micro-batch processing（default）
+ continuous processing（spark 2.3 introduce）

## 基本概念
将实时流数据当做成一张不断追加的表(无界表)，(Data Stream = unbounded table)  
数据流中新的数据 = 无界表中新追加的一行(Row) 

## Programming Model
Input -> Result -> Output   
注意：Structured Streaming并不保存原始流数据，只保存中间结果数据   
用户对流处理无感知，与静态批处理类似   

## Output Mode 
+ Complete Mode 完整结果  
+ Append Mode   追加结果(new rows)  
+ Update Mode   有修改的结果追加(updated rows)  

## offset
每个数据流都有offset，类似kafka offset   
checkpoint and write-ahead logs record the offset    
流处理过程是幂等的   



