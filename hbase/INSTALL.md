# install HBase

管理角色：HMaster，一般2台，一台active，一台backup
数据节点角色：HRegionServer，应该与hdfs中的datanode安装在一起  

## install

1. 修改hbase-env.sh
```
export JAVA_HOME=...
//HBase自带的ZK
export HBASE_MANAGES_ZK=false
```

2. 修改hbase-site.xml
```
<configuration> 
    <!-- 指定hbase在HDFS上存储的路径 -->
    <property>
        <name>hbase.rootdir</name>
        <value>hdfs://hdp01:9000/hbase</value>
    </property>
    
    <!-- 指定hbase是分布式的 -->
    <property>
        <name>hbase.cluster.distributed</name>
        <value>true</value>
    </property>
    
    <!-- 指定zk的地址，多个用“,”分割 -->
    <property>
        <name>hbase.zookeeper.quorum</name>
        <value>hdp01:2181,hdp02:2181,hdp03:2181</value>
    </property>
</configuration>
```

3. 修改regionservers
RegionServer的机器名

4. 启动hbase
```
//一次性启动
bin/start-hbase.sh

//启动单个
bin/hbase-daemon.sh start master
```

## 问题

1. HBase与Hadoop jar包冲突 org.slf4j.impl.Log4jLoggerFactory
删除其中一个jar即可
```
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/Users/ldeng/hadoop/hadoop-3.1.2/share/hadoop/common/lib/slf4j-log4j12-1.7.25.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/Users/ldeng/bigdata/hbase-2.1.5/lib/client-facing-thirdparty/slf4j-log4j12-1.7.25.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.slf4j.impl.Log4jLoggerFactory]
```

2. 查看日志报错： java.lang.ClassNotFoundException: org.apache.htrace.SamplerBuilder
```
cd $HBASE_HOME
cp lib/client-facing-thirdparty/htrace-core-3.1.0-incubating.jar lib/
```

3. ZK报错
```
Could not start ZK at requested port of 2181.  ZK was started at port: 2182.  Aborting as clients (e.g. shell) will not be able to find this ZK quorum.

<property>
  <name>hbase.cluster.distributed</name>
  <value>true</value> 
</property>
```

4. Master报错
```
java.lang.IllegalStateException: The procedure WAL relies on the ability to hsync for proper operation during component failures, but the underlying filesystem does not support doing so

<property>
   <name>hbase.unsafe.stream.capability.enforce</name>
   <value>false</value>
</property>
```

5. RegionServer报错
```
java.lang.NoClassDefFoundError: Could not initialize class org.apache.hadoop.hbase.io.asyncfs.FanOutOneBlockAsyncDFSOutputHelper

<property>
   <name>hbase.wal.provider</name>
   <value>filesystem</value>
</property>
```

## web管理页面
```
http://localhost:16010/
```

## 客户端
```
bin/hbase shell
```