Mysql远程连接配置
=====

环境：unbuntu 16.04

最新版本的Mysql在远程连接的配置上与老版本有了一些出入，照原先的配置已经不行了，所以在这里记录一下遇到的所有新问题。

配置远程连接的步骤如下：

## 配置权限
进入Mysql，然后输入如下的语句：
```
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'mypassword' WITH GRANT OPTION;

FLUSH PRIVILEGES;
```
上面的%可以改成一个具体的IP地址，这样就只允许这个IP访问了。


## 配置监听IP
上面的配置是不够的。Mysql默认只监听127.0.0.1这个IP地址。所以需要更改`/etc/mysql/mysql.conf.d/mysqld.cnf`文件。
里面有一个`bind-address=127.0.0.01`，改成:
```
bind-address=0.0.0.0
```

重启mysql: `service mysql restart`
通过`netstat -ant |grep mysql` 来查看是否监听的IP地址变化了。


## 配置防火墙
通过`iptables -L -n` 查看是否防火墙有限制。一般新版的ubuntu系统已经不安装iptables模块了。不过还是看一眼比较好。


## 虚拟机网络配置
经过上面的配置你就可以通过 `mysql -uroot -p -P3306 -h<mysql_host_ip>`来连接了。但是如果你是使用虚拟机，例如阿里云的虚拟机，那么你还需要配置虚拟机的网络。因为虚拟机的IP地址并不是公网IP地址，而是有一个端口映射。进入云控制台，修改虚拟机的网络来配置一个端口镜像，让虚拟机的私有IP地址+3306端口映射到公网IP地址+3306上。


如果上述方法还是无法连接，那你需要通过telnet ipaddress 3306来看一下是否能连接上。


@完












