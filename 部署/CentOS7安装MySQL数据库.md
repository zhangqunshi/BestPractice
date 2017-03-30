# 介绍
安装环境： CentOS Linux release 7.3.1611 (Core)

## 安装MySQL数据库
从网上找到下面命令:

    #yum install mysql-server
    #yum install mysql-devel
    #yum install mysql

安装mysql-server会出现失败，如下输出

    [root@knl ~]# yum install mysql-server
    Loaded plugins: fastestmirror
    Loading mirror speeds from cached hostfile
     * base: mirrors.tuna.tsinghua.edu.cn
     * extras: mirrors.btte.net
     * updates: mirrors.btte.net
    No package mysql-server available

查资料发现是CentOS7版本将MySQL数据库软件从默认的程序列表中移除，用mariadb代替了。:(

有两种解决办法：
### 方法一：安装mariadb
MariaDB数据库管理系统是MySQL的一个分支，主要由开源社区在维护，采用GPL授权许可。开发这个分支的原因之一是：甲骨文公司收购了MySQL后，有将MySQL闭源的潜在风险，因此社区采用分支的方式来避开这个风险。**MariaDB的目的是完全兼容MySQL，包括API和命令行，使之能轻松成为MySQL的代替品。**

安装mariadb，大小59 M。

    [root@yl-web yl]# yum install mariadb-server mariadb 

安装过程
```
=========================================================================================================================================================================================================
 Package                                                    Arch                                      Version                                              Repository                               Size
=========================================================================================================================================================================================================
Installing:
 mariadb                                                    x86_64                                    1:5.5.52-1.el7                                       base                                    8.7 M
 mariadb-server                                             x86_64                                    1:5.5.52-1.el7                                       base                                     11 M
Installing for dependencies:
 perl-Compress-Raw-Bzip2                                    x86_64                                    2.061-3.el7                                          base                                     32 k
 perl-Compress-Raw-Zlib                                     x86_64                                    1:2.061-4.el7                                        base                                     57 k
 perl-DBD-MySQL                                             x86_64                                    4.023-5.el7                                          base                                    140 k
 perl-DBI                                                   x86_64                                    1.627-4.el7                                          base                                    802 k
 perl-IO-Compress                                           noarch                                    2.061-2.el7                                          base                                    260 k
 perl-Net-Daemon                                            noarch                                    0.48-5.el7                                           base                                     51 k
 perl-PlRPC                                                 noarch                                    0.2020-14.el7                                        base                                     36 k

Transaction Summary
=========================================================================================================================================================================================================
Install  2 Packages (+7 Dependent packages)

Total download size: 21 M
Installed size: 107 M
Is this ok [y/d/N]: y
```
上面要输出y，然后回车。

mariadb数据库的相关命令是：

    - systemctl start mariadb  #启动MariaDB
    - systemctl stop mariadb  #停止MariaDB
    - systemctl restart mariadb  #重启MariaDB
    - systemctl enable mariadb  #设置开机启动

记得启动MySQL数据库服务。

### 方法二：官网下载安装mysql-server

首先下载：
```
# wget http://dev.mysql.com/get/mysql-community-release-el7-5.noarch.rpm
# rpm -ivh mysql-community-release-el7-5.noarch.rpm
yum install mysql-community-server
```
(最后一步我没有成功，可能是yum源有区别)

安装成功后重启mysql服务。

    # service mysqld restart

初次安装mysql，root账户没有密码。直接通过`mysql -uroot`进入。


## 配置MySQL
mysql配置文件为/etc/my.cnf

### 编码方式
最后加上编码配置

    [mysql]
    default-character-set=utf8

这里的字符编码必须和/usr/share/mysql/charsets/Index.xml中一致。


### 设置密码
把在所有数据库的所有表的所有权限赋值给位于所有IP地址的root用户。

    mysql> grant all privileges on *.* to root@'%'identified by 'password';
    mysql> mysql> flush privileges;
如果是新用户而不是root，则要先新建用户

    mysql>create user 'username'@'%' identified by 'password';  
此时就可以进行远程连接了。


----------------
参考：
- http://www.cnblogs.com/starof/p/4680083.html


