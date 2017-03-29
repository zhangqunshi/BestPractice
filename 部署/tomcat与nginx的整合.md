Tomcat与Nginx的整合
===================
# 环境
操作系统：ubuntu 14.04.4 LTS


# 安装Nginx
有两种方式，一种是使用apt-get命令来安装二进制版本，另外一种是下载源码后自己编译。

## 二进制安装 (需要联网)
这种安装方式比较简单。直接运行：
```
sudo apt-get install nginx
```
:) 真的好方便呀。就是版本有点低：nginx/1.4.6

安装后文件的目录位置：
1. 服务地址：/etc/init.d/nginx
2. 配置地址：/etc/nginx/　　如：/etc/nginx/nginx.conf
3. Web默认目录：/usr/share/nginx/http/　　如：usr/share/nginx/index.html
4. 日志目录：/var/log/nginx/　　如：/var/log/nginx/access.log
5. 主程序文件：/usr/sbin/nginx


## 源代码安装 (可以不联网)

### 下载Nginx

首先在官网中下载所需版本：http://nginx.org/en/download.html。

目前我使用的版本是Linux境下最新稳定版1.10.3。

下载完毕后，解压nginx-1.10.3.tar.gz。

```bash
wget http://nginx.org/download/nginx-1.10.3.tar.gz
tar zxvf nginx-1.10.3.tar.gz
```

TODO: 待续



# 配置Nginx
首先进入conf目录`cd /etc/nginx`，在修改nginx配置之前，把原始配置文件备份一下。
目录包括文件为：

    # ls -l
    total 64
    drwxr-xr-x 2 root root 4096 Oct 27 23:38 conf.d
    -rw-r--r-- 1 root root  911 Mar  5  2014 fastcgi_params
    -rw-r--r-- 1 root root 2258 Mar  5  2014 koi-utf
    -rw-r--r-- 1 root root 1805 Mar  5  2014 koi-win
    -rw-r--r-- 1 root root 2085 Mar  5  2014 mime.types
    -rw-r--r-- 1 root root 5287 Mar  5  2014 naxsi_core.rules
    -rw-r--r-- 1 root root  287 Mar  5  2014 naxsi.rules
    -rw-r--r-- 1 root root  222 Mar  5  2014 naxsi-ui.conf.1.4.1
    -rw-r--r-- 1 root root 1601 Mar  5  2014 nginx.conf
    -rw-r--r-- 1 root root  180 Mar  5  2014 proxy_params
    -rw-r--r-- 1 root root  465 Mar  5  2014 scgi_params
    drwxr-xr-x 2 root root 4096 Mar 21 12:02 sites-available
    drwxr-xr-x 2 root root 4096 Mar 21 12:02 sites-enabled
    -rw-r--r-- 1 root root  532 Mar  5  2014 uwsgi_params
    -rw-r--r-- 1 root root 3071 Mar  5  2014 win-utf


conf.d目录用来保存配置，但一般不用。
nginx.conf中已经include了site-enabled下面的配置文件：

> include /etc/nginx/conf.d/*.conf;
> include /etc/nginx/sites-enabled/*;

所以不要改nginx.conf文件了，直接修改site-enabled中的配置文件。
sites-available目录中的文件和site-enabled中的配置文件是同一个文件。
> lrwxrwxrwx 1 root root 34 Mar 21 12:02 default -> /etc/nginx/sites-available/default

所以修改两个目录下的default文件都一样。

修改vim命令修改配置文件: 
`vim /etc/nginx/sites-available/default`

编辑location部分：

    location / {
        # First attempt to serve request as file, then
        # as directory, then fall back to displaying a 404.
        #try_files $uri $uri/ =404;
        # Uncomment to enable naxsi on this location
        # include /etc/nginx/naxsi.rules

        proxy_pass http://localhost:8080;
    }

    location ~ \.(gif|jpg|jpeg|png|bmp|swf)$ {
        root /opt/apache-tomcat-8.5.8/webapps/ROOT;
        expires 30d;
    }



# 启动Nginx服务
修改配置后，重启生效，输入命令：
```
sudo service nginx restart
```


# 参考文档
安装文档: <http://nginx.org/en/docs/install.html>
使用文档: <http://nginx.org/en/docs/beginners_guide.html>
