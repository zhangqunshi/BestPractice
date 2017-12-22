MongoDB日志文件过大
=======

MongoDB启动的时候指定了--logpath为一个日志文件。随着时间此文件会变得越来越大，达到好几个G。
因为不想让MongoDB进程重新启动，所以不能停止进程删除此文件。解决的方法网上给方式有两种。

## 第一种方式
进入到mongo的命令行交互程序，输入：
```
use admin
db.runCommand({logRotate: 1})
```
经过测试发现，此种方式只能让主mongo的日志滚动。如果是集群部署的话，其他机上的mongo日志并不会滚动。
所以还需要使用下面的方式。


## 第二种方式
在Linux的命令上输入如下命令：
```
ps aux|grep mongo
```
找到mongodb的进程ID，记下来。在输入命令：
```
kill -SIGUSR1 <pid>
```
此种方式只能修改当前机器上的mongo日志滚动。如果是集群部署，需要在所有运行mongodb的机上执行此命令。

--------
参考：
1. <https://docs.mongodb.com/manual/tutorial/rotate-log-files/>
2. <http://blog.csdn.net/csfreebird/article/details/26165157>

