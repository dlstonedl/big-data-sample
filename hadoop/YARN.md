# YARN集群
Apache Hadoop YARN(Yet Another Resource Negotiator,另一种资源协调者)，它是一个通用的资源管理
系统，可为上层应用提供统一的资源管理和调度；

## Resource Manager
RM是一个全局的资源管理器，负责整个系统的资源管理与分配；
两个组件：调度器(Scheduler)和应用程序管理器(Applications Manager,ASM)

Scheduler：根据容量，队列等限制条件，将系统中的资源分配给各个正在运行的应用程序；资源分配单位用一个抽象
概念Resource Container表示，Container是一个动态资源分配单位；Scheduler是一个可插拔的组件；

ASM：负责管理整个系统所有应用程序，包含应用程序提交，与Scheduler协商资源已启动ApplicationMaster，
监控ApplicationMater运行状态并在失败时重启它

有且仅有一个Resource Manager；    
任务调度；建议不与NameNode安装在一起；一般情况下单独安装； 

## Node Manager
NM，节点管理器，每个节点上的资源和任务管理器；
定时向RM汇报本节点上的资源使用情况和各个Container的运行状态；
接收并处理来自AM的Container的启动/停止等各种请求；

可以有多个Node Manager；
创建容器运行程序(任务)，一般情况下，与DataNode安装在一起(方便读取数据)，也可分开安装；           
每个Node Manager，可以创建多个app容器(CPU + 内存)，并接收客户端传过来的jar包；       
接收客户端参数，启动MapTask or ReduceTask； 

## ApplicationMaster
AM，用户提交的每个应用程序均包含一个AM
与RM Scheduler协调以获取资源（用Container表示）
将得到的资源进一步分配给内部的任务（资源的二次分配）
与NodeManager通信以启动/停止任务
监控搜索任务运行状态，并在任务运行失败时，重新为任务申请资源，重启任务

## Container
YARN中资源的抽象，封装节点上的多维度资源，如内存、CPU、磁盘、网络等；
当AM向RM申请资源时，RM为AM返回的资源用Container表示；
YARN为每个任务分配一个Container，该任务只能使用该Container中的资源；

特定的应用执行由AM控制，AM负责将一个应用分隔成多个任务，并和RM协调执行所需的资源；
资源分配完毕后，AM和NM一起安排、执行、监控独立的应用任务；

Application -> Task

YARN主从架构：
Slave -- NM
Master -- RM

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

问题：
> localhost: ssh: connect to host localhost port 22: Connection refused

MAC:
1. 系统：选择系统偏好设置->选择共享->点击远程登录
2. cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys

yarn管理页面：
> http://localhost:8088