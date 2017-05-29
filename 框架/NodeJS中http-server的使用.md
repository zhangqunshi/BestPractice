Node.js中http-server的使用
=======


## 使用阿里的npm镜像
国外的npm太慢了。查看一下自己使用的源：
```
npm config get registry
```
应该显示<https://registry.npmjs.org/>。这回改成阿里的：
```
npm config set registry https://registry.npm.taobao.org
```
不过在npm publish时要记得切回去



## 安装http-server
http-server是一个基于Node.js的简单零配置命令行HTTP服务器. 如果你不想重复的写Node.js的web-server.js, 则可以使用这个.
安装命令:
```
npm install http-server -g
```

启动http-server命令就是在cmd下直接输入`http-server`，之后就可以浏览器访问<http://localhost:8080>.
默认web目录是当前目录，想改变web目录的话，在命令后面加上本地路径，例如：
```
http-server <path_of_project>
```
查看帮助使用`http-server --help`，如果想改变端口和地址采用如下方式：
```
http-server <path> -a 0.0.0.0 -p 8080
```
参数`-a`是监听地址，而参数`-p`是修改监听端口。




------------
参考:

- <http://www.cnblogs.com/leee/p/5502727.html>
