uwsgi错误invalid request block size
===========================
今天使用uwsgi启动django代码，然后打开浏览器输入http://localhost:8000/admin。后台出现下面错误

    invalid request block size: 21573 (max 4096)...skip
    
## 原因
我想起来我是使用nginx来把请求发送给uwsgi。所以uwsgi被配置成使用socket方式（为tcp协议）进行通信。如果打开浏览器访问uwsgi指定的端口，那么浏览器请求uwsgi的方式为http协议，而不是socket方式。所以就导致uwsgi的log文件中打出上面的错误信息。

如果你想临时使用http访问uwsgi服务。那么需要把之前的uwsgi服务停止，并使用下面命令来启动
```
uwsgi --http :8000 --wsgi-file application.py
```
如果是使用uwsgi.ini配置文件，那么修改里面内容把socket=:8000替换成http=:8000。
然后再次启动`uwsgi --ini /patch/to/uwsgi.ini`。

注意：以上两种方式启动不能混用，例如使用`uwsgi --http :8000 --ini /path/to/uwsgi.ini`会造成端口已经被占用的错误:

```
uWSGI http bound on :8000 fd 3
probably another instance of uWSGI is running on the same address (:8000).
bind(): Address already in use [core/socket.c line 769]
```

@完

---------------
参考: <http://www.tuicool.com/articles/2Araye>