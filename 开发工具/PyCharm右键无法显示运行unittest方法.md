PyCharm鼠标右键不显示Run unittest方法
=========================
PyCharm是一个用来写python代码的IDE，很好用。在其中建立了unittest类后，鼠标点击某个test方法后，菜单中会显示Run unittest方法。

## 问题描述
今天发现一个问题，在pycharm上鼠标右键不显示Run unittest方法。而相同的IDE上另外一个项目可以显示。这真是奇怪了(+﹏+)~


## 问题原因
后来才知道这个项目是由CLion建立出来的，然后上传到Git上。PyCharm和CLion是同一家公司，推测其项目的配置文件不兼容。PyCharm还居然可以导入CLion建立的工程，无语了╮(╯_╰)╭，这算不算PyCharm的bug呀？


## 解决办法
把这个项目中所在文件中的.idea目录整个删除掉。然后新建一个PyCharm的项目，还是使用相同的项目所在路径。同时把.idea目录加入到gitignore中，因为项目工程每个人都不一样，没必要上传项目配置文件。

@完