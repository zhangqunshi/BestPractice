# Django安装介绍

安装环境： CentOS7

安装Django比较简单，但需要安装其依赖的东西，还是需要一定时间的。我使用的环境是CentOS Linux release 7.3.1611。
内核为：3.10.0-514.10.2.el7.x86_64。

## 安装Django
首先需要安装pip命令，看一下我写的另外一篇`pip安装介绍`文章。
然后运行下面命令
```
    pip install django
```

## 安装MySQL数据库
这部分信息量太大，看我写的另外一篇文章《CentOS7安装MySQL数据库》。

简略的方法就是：

    yum install mariadb-server mariadb 

然后登录mysql运行

    mysql> grant all privileges on *.* to root@'localhost' identified by 'password';
    mysql> flush privileges;

建立数据库
```
CREATE DATABASE <dbname> CHARACTER SET utf8; 
```

## 安装MySQL驱动
如果遇到错误`EnvironmentError: mysql_config not found`说明你还没有安装MySQL或者dev包。或者安装的不正确。
首先运行下面命令
```
yum install mysql-devel
yum install MySQL-python
```

验证一下安装是否正确

    [root@hello ~]# python
    Python 2.7.5 (default, Nov  6 2016, 00:28:07) 
    [GCC 4.8.5 20150623 (Red Hat 4.8.5-11)] on linux2
    Type "help", "copyright", "credits" or "license" for more information.
    >>> import MySQLdb
    >>> db =  MySQLdb.connect("localhost","root","password","")
    >>> cursor =  db.cursor()
    >>> cursor.execute("SELECT VERSION()")
    1L
    >>> data = cursor.fetchone()
    >>> print "Database version : %s" % data
    Database version : 5.5.52-MariaDB
    >>> db.close()
    >>> 
    >>> exit()

## 安装ZeroMQ
如果项目中使用了这个软件，可以用下面的命令安装：
```
pip install pyzmq
```

## 安装Redis
网上通过`yum -y install redis`命令来安装redis在我这里没有成功，yum源估计不行。所以只能手动安装了。具体命令为：
```
$ wget http://download.redis.io/releases/redis-3.2.8.tar.gz
$ tar xzf redis-3.2.8.tar.gz
$ cd redis-3.2.8
$ make
```
make的时间会长一些。最后会看到这句话Hint: It's a good idea to run 'make test' ;)

启动redis
```
[root@hello redis-3.2.8]# src/redis-server
```
记得改成采用后台方式启动，然后运行一下`src/redis-cli`来看一下是否能插入数据：
```
set kris hello
get kris
del kris
```

## 安装Redis的python驱动

采用pip命令进行安装:
```sh
pip install redis
```
另外还可以选择安装parser
```sh
pip install hiredis
```

## 安装psutil
```
yum install python-devel
pip install psutil
```
如果第一步骤的python开发包不安装，那么会出现error: command 'gcc' failed with exit status 1


## 安装Nginx
运行命令
```
$ wget http://nginx.org/packages/centos/7/x86_64/RPMS/nginx-1.10.3-1.el7.ngx.x86_64.rpm
$ rpm -ivh nginx-1.10.3-1.el7.ngx.x86_64.rpm
```
安装之后需要修改配置文件/etc/nginx/conf.d/default.conf文件。
增加如下部分：
```
location / {
    root /path/frontend/html/;
    index index.html index.htm;
    expires 30d;
    add_header Cache-Control private;	
    
}

location /api/ {
    include uwsgi_params;
    uwsgi_pass 127.0.0.1:8001;
    uwsgi_read_timeout 2;
}
```
启动运行命令`systemctl start nginx`。

## 安装uwsgi
```
$ pip install uwsgi
```
Successfully installed uwsgi-2.0.15显示后，说明安装完成。
接下来需要配置一下uwsgi，在你的Django项目所在目录下建立一个uwsgi.ini文件。(文件名可以改)
内容如下：
```
[uwsgi]
socket=:9000
chdir=/path/to/yourproject
module=yourproject.wsgi:application
master=True
processes=10
enable-threads=true
pidfile=/var/run/uwsgi.pid
vacuum=True
max-requests=5000
daemonize=/var/log/yourproject.log
```
记得把上面的yourproject替换成你项目的名称。之后就启动uwsgi进程
```
uwsgi --ini /path/to/yourproject/uwsgi.ini
```

使用Django提供的命令来把静态文件导出到settings.py中配置STATIC_ROOT的目录中。 
```sh
python manage.py collectstatic
```

## 建立数据库表
执行下面命令：
```
python manage.py makemigrations
python manage.py migrate
```

## 运行Django
执行`python manage.py runserver`，然后打开浏览器查看是否能访问主页。


以上亲测可行.

@完

----------------------
参考
- http://blog.csdn.net/xingshunkai/article/details/46821711
- https://pyzmq.readthedocs.io/en/latest/
- http://zeromq.org/bindings:python


