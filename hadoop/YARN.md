# YARN集群

## Resource Manager
有且仅有一个Resource Manager；    
任务调度；建议不与NameNode安装在一起；一般情况下单独安装；    


## Node Manager
可以有多个Node Manager；
创建容器运行程序(任务)，一般情况下，与DataNode安装在一起(方便读取数据)，也可分开安装；           
每个Node Manager，可以创建多个app容器(CPU + 内存)，并接收客户端传过来的jar包；       
接收客户端参数，启动MapTask or ReduceTask； 

## 配置文件
yarn-site.xml    

```
<configuration>
<property>
<name>yarn.resourcemanager.hostname</name>
<value>localhost</value>
</property>

<property>
<name>yarn.nodemanager.aux-services</name>
<value>mapreduce_shuffle</value>
</property>
</configuration>
```

## 启动YARN集群
```
在Resource Manager机器上运行如下命令：
start-yarn.sh 
stop-yarn.sh
```

待解决问题：
> localhost: ssh: connect to host localhost port 22: Connection refused

MAC:
1. 系统：选择系统偏好设置->选择共享->点击远程登录
2. cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys

yarn管理页面：
> http://localhost:8088