CentOS7关闭防火墙
==================
每一次系统更新都带来不少的变化，导致我们要再重新学习，很伤呀。╮(╯﹏╰)╭

CentOS7的防火墙不是之前的iptables了，改成firewall了。

## 关闭防火墙
```
systemctl stop firewalld.service
```
成功了也没有输出，所以不用等了，直接看一下是否规则都没有了`iptables -L -n`，如果看不到任何规则就说明一金关闭了。

## 开机不启动防火墙
要想永久干掉防火墙，那么执行下面的命令
```
systemctl disable firewalld.service
```


@完

--------------
参考: <http://www.linuxidc.com/Linux/2015-05/117473.htm>