NodeJS全局安装与本地安装
=======

## 全局安装

```
npm install <module_name> -g 
或 
npm install <module_name> --global
```

可以使用`npm root -g`查看全局安装目录。
全局安装不能直接通过require()的方式去引用模块，需要设置环境变量的NODE_PATH为C:\Program Files\nodejs。

不推荐只全局安装，因为版本不好控制。


## 本地安装
```
npm install <module_name>
或 
npm install <module_name> --save-dev
```

其中参数--save-dev的含义是代表把你的安装包信息写入package.json文件的devDependencies字段中.

包安装在当前项目的node_modules文件夹下.

本地安装可以让每个项目拥有独立的包，不受全局包的影响，方便项目的移动、复制、打包等，保证不同版本包之间的相互依赖，这些优点是全局安装难以做到的。

