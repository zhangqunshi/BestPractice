Nginx的Permission denied错误
============
环境: CentOS7


## 问题描述
今天搭建了nginx+uwsgi+django的环境，之后通过浏览器访问遇到下面问题：

 >2017/03/31 19:38:13 [crit] 191969#191969: *5 connect() to 127.0.0.1:8000 failed (13: Permission denied) while connecting to upstream, client: 192.168.0.10, server: localhost, request: "GET /admin HTTP/1.1", upstream: "uwsgi://127.0.0.1:8000", host: "192.168.0.20:8080"

上面来自/var/log/nginx/error.log。


## 原因
通过ps命令查看nginx进程为用户nginx，而uwsgi进程为用户root。估计是权限不一致导致nginx无法访问uwsgi进程（socket方式通信）。
所以修改nginx的配置文件/etc/nginx/nginx.conf，把user选项从nginx改成root。之后执行重启命令`systemctl restart nginx`。

再次打开浏览器访问django，发现仍然不行。(+﹏+)~狂晕

上网继续调查，发现还有一个原因可能引起这个问题，那就是selinux，执行下面命令：
```
setsebool -P httpd_can_network_connect 1
```
然后重新访问浏览器，发现可以打开页面了。如果还不行就彻底一些，关闭selinux：
```
setenforce 0
```

O(∩_∩)O哈哈~，终于搞定了。

## One More Thing
在stackoverflow网站看到一个回复是说使用root运行uwsgi还是不太好的，建议使用一个和nginx相同的普通账户运行。例如：
```
uwsgi -s /tmp/uwsgi.sock -w my_app:app --uid www-data --gid www-data
```


@完

-----------
参考：
- <http://www.cnblogs.com/zengjfgit/p/5910345.html>
- <http://blog.csdn.net/scorpio3k/article/details/48847611>
- <http://stackoverflow.com/questions/21820444/nginx-error-13-permission-denied-while-connecting-to-upstream>

