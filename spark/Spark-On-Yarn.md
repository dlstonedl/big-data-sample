# Spark-On-Yarn

+ 需要安装HDFS和YARN，spark运行时，把jar存放在HDFS上
+ 需要修改spark-env.sh配置文件
```
export JAVA_HOME=XXX
export HADOOP_CONF_DIR=XXX
```

## 运行模式
1. cluster模式
```
./bin/spark-submit --class org.apache.spark.examples.SparkPi \
--master yarn \
--deploy-mode cluster \
--driver-memory 1g \
--executor-memory 1g \
--executor-cores 2 \
--queue default \
lib/spark-examples*.jar \
10
```

2. client模式
```
./bin/spark-submit --class org.apache.spark.examples.SparkPi \
--master yarn \
--deploy-mode client \
--driver-memory 1g \
--executor-memory 1g \
--executor-cores 2 \
--queue default \
lib/spark-examples*.jar \
10
```

如果使用client模式报错，需要修改yarn-site.xml配置文件
```
<property>
    <name>yarn.nodemanager.pmem-check-enabled</name>
    <value>false</value>
</property>

<property>
    <name>yarn.nodemanager.vmem-check-enabled</name>
    <value>false</value>
</property>
```

spark-shell必须使用client模式
```
./bin/spark-shell --master yarn --deploy-mode client
```

3. 两种模式的区别

cluster(生产使用)
SparkSubmit            提交任务
ApplicationMaster      Driver,由Yarn集群控制

client
SparkSubmit            Driver
ExecutorLauncher       申请资源

注意：spark-shell，spark-sql，需要使用client模式
