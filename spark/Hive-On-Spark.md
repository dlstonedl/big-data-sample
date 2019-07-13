#Hive On Spark

1. 安装MySQL(Hive的元数据库)并创建一个普通用户，并且授权；
2. 在spark的conf目录下创建一个Hive的配置文件(hive-site.xml);
3. 启动spark-sql(sparkSubmit需要连接Mysql，获取元数据信息)；
```
./spark-sql --master spark://node-4:7077,node-5:7077 
--driver-class-path ${driver-class-path} 
```
4. sparkSQL会在mysql上创建一个database，需要手动改一下DBS表中的DB_LOCATION_URI改成hdfs的地址；
5. 在环境变量中配置HADOOP_CONF_DIR,让SparkSQL知道hdfs中NameNode的位置；
6. 重新启动sparkSQL命令行；

使用Hive的标准，给Hive换了一个执行引擎(MR -> Spark)
Hive本身使用的是MR

