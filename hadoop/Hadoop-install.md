## 安装HADOOP
1. 指定Hadoop的默认文件系统：hdfs  
2. 指定hdfs的namenode节点为哪台机器  
3. 指定namenode软件存储元数据的本地目录  
4. 指定datanode软件存放文件块的本地目录 

注意：需要关闭防火墙   
安装使用的Hadoop版本: hadoop-3.1.2, 与2.X有些许区别，比如端口号  
hadoop官网：https://hadoop.apache.org/  

## hadoop配置文件
目录：${install-path}/etc/hadoop   
1. 修改配置文件hadoop-env.sh，配置JAVA_HOME  
> export JAVA_HOME=/XXX  
2. 修改core-site.xml 
```
<configuration>
<property>
<name>fs.defaultFS</name>
<value>hdfs://localhost:9000</value>
</property>
</configuration>
```
3. 修改hdfs-site.xml
```
<configuration>
<property>
<name>dfs.namenode.name.dir</name>
<value>${namenode-data-path}</value>
</property>

<property>
<name>dfs.datanode.data.dir</name>
<value>${datanode-data-path}</value>
</property>
</configuration>
```
## 启动HDFS
1. 配置hadoop环境变量   
配置HADOOP_HOME及PATH    

2. 初始化namenode的元数据目录  
> hdfs namenode -format

3. 启动namenode进程，使用jps查看进程  
> hdfs --daemon start namenode  

打开namenode的web窗口：  
> hadoop2.X port=50070    
> hadoop3.x port=9870 
   
打开：http://localhost:9870 
   
4. 启动datanode进程  
> hdfs --daemon start datanode   

打开datanode的web窗口：  
> hadoop2.X port=50075      
> hadoop3.x port=9864    

打开：http://localhost:9864  


