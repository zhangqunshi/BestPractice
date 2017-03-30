# 介绍
本人想在Ubuntu上开发Python程序，使用MySQL数据库。

安装环境： Ubuntu14.04

## 安装MySQL数据库
具体步骤如下：

    apt-get update
    apt-get install python-pip (已经有pip命令则跳过此步骤)

    apt-get install mysql-server
    apt-get install mysql-client

## 安装mysqlclient
具体步骤如下：

    apt-get install libmysql-dev
    apt-get install libmysqlclient-dev
    apt-get install python-dev
    pip install mysqlclient

## 其他
在windows上开发
- 如果你使用Python2.7,下载网站为：https://pypi.python.org/pypi/mysqlclient。
- 如果你使用Python3.5,下载网站为：http://www.lfd.uci.edu/~gohlke/pythonlibs/#mysql-python

我下载的是 mysqlclient-1.3.9-cp35-cp35m‑win_amd64.whl
它是wheel包管理端的。所以需要安装wheel包支持。
```
$ pip install wheel
$ pip install /path/mysqlclient.whl
```

### 错误1：is not a supported wheel on this platform.
这个错误是由于版本不对应。cp27是指python2.7版本。
下载与你自己安装的python版本对应的版本就OK了。
https://my.oschina.net/HIJAY/blog/484759

----------------
参考：
- http://www.ctolib.com/topics/46743.html
- http://www.cnblogs.com/stones/p/5833845.html
