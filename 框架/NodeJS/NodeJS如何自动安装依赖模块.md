Node.js如何自动安装所有依赖模块
=====


在代码中require模块x, 但本地没有模块x 需要npm install x安装。但是一个一个安装太麻烦。使用下面的命令一次性安装所有依赖：
```
npm install
```
注意：要有package.json文件，并且dependencies字段已经指定好依赖的包名。