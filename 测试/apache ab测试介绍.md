apache ab测试介绍
=====

## 安装ab命令
环境为ubuntu16.04.2 LTS，安装的命令为：
```
sudo apt-get install apache2-utils
```

## 使用说明
格式为：`ab [options] [http[s]://]hostname[:port]/path`

参数比较多，可以通过`ab --help`来查看。举一个例子：
```
ab -n 100 -c 10 http://localhost:8080/hello
```
上面的命令是指对`http://localhost:8080/hello`这个url请求100次，其并发请求为10（每次同时发10个请求）。

@完

参考：<http://blog.csdn.net/fdipzone/article/details/9090625>
