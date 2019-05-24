# HDFS

## HDFS命令行客户端
```
hadoop fs -h  
hadoop fs -ls /  
hadoop fs -put <localsrc> <dst>  
hadoop fs -get <src> <localsrc> 
``` 
新版本命令行
```
hdfs dfs -ls /
```

待解决的问题(不影响使用，可以去namenode中查看文件信息) 
```
Unable to load native-hadoop library for your platform... using builtin-java classes where applicable

可以使用hdfs dfs -ls /
```
## namenode元数据管理机制
内存(树)  
fsimage(内存元数据对象序列化到磁盘)  
edits日志文件(滚动操作日志)  

secondary namenode  
checkpoint机制：下载edits日志文件，并合并edits日志文件至fsimage，然后上传namenode   

## 写数据流程

1. 访问namenode，请求写  
2. namenode告知可写
3. 请求写入第一个block
4. 返回一系列datanode主机
5. 挑选一台datanode，请求建立连接
6. datanode之间建立链式连接
7. 全部连接建立之后，层层返回响应
8. 客户端传输第一个block的数据，datanode之间链式传递block数据
9. 重复3-8，传输下一个block
10. 数据全部写完后，告知namenode
11. namenode确认记录元数据

只需要有一个datanode写成功即可，副本可由namenode同步；  
如果第一个datanode写失败，会重试namenode返回的下一个datanode；  
如果最后一个block失败，namenode删除元数据，并由namenode在与datanode通信时，删除脏数据；  
分布式系统的复杂性，在于异常情况的处理，而不是正常的业务流程；   

## 读数据流程

1. 客户端请求namenode，请求元数据； 
2. 根据元数据，挑选datanode，请求每个block的数据；  
3. 读取block数据，并拼接；  
