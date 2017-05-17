CentOS7.0安装Nginx
===================
安装nginx有多种方式，下面依次介绍

## RPM包安装
这种安装方式还分为手动下载RPM包，或者通过yum源下载RPM包。

### 手动下载RPM包
这种方式是从nginx官方网站上下载nginx的rpm包，然后手动安装到系统中。
```
$ wget http://nginx.org/packages/centos/7/x86_64/RPMS/nginx-1.10.3-1.el7.ngx.x86_64.rpm
$ rpm -ivh nginx-1.10.3-1.el7.ngx.x86_64
```

### 建立yum源下载并安装
另外一种方式是在/etc/yum.repos.d/目录下建立nginx.repo文件，内容如下：
```
[nginx]
name=nginx repo
baseurl=http://nginx.org/packages/centos/7/$basearch/
gpgcheck=0
enabled=1
```
然后运行命令`yum install nginx`就可以完成安装了。
如果你不知道系统是32位还是64位，建议使用这种安装方式比较好。

## 源代码编译安装

@ 待续


## 配置并运行
配置文件在/etc/nginx/conf.d目录下的default.conf。
运行命令为`systemctl start nginx`
![nginx](http://images2015.cnblogs.com/blog/919970/201702/919970-20170224155606601-862673866.png)


--------------
参考
- <http://nginx.org/en/docs/install.html>
- <http://www.cnblogs.com/garfieldcgf/p/6438898.html>
