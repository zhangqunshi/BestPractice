sqlite3使用
===========

sqlite可以在<https://www.sqlite.org/>网站上下载。支持Linux, MacOS, Windows等各种操作系统。下载后不需要安装，解压后就可以使用，追加路径到path中，让其可以在任何目录下都可以使用。

## 进入命令行交互环境

如果已经有了数据库，则在cmd中命令为：
```
sqlite3 dbfile
```
没有的话就直接输入`sqlite3`回车。

## 查看帮助
sqlite所有的命令前面都带有一个点，例如查看帮助信息的命令为:
```
sqlite> .help

```


## 查看当前数据库
```
sqlite> .database
```

注意不用输入分号结束


## 查看所有表
```
sqlite> .tables
```


## 查看某个表的结构
```
sqlite> .schema tablename
```

## 导出数据库
在交互式命令行下为：
```
sqlite> .output backup.sql
sqlite> .dump
```


退出交互式命令行后，可以使用如下命令
```
sqlite3 dbfile .dump > db_backup.sql
```

## 导入数据库
```
sqlite> .separator ","
sqlite> .import datafile tablename
```

datafile中第一行为列名，剩下行为数据，而tablename不用存在，而是在导入过程中创建。

通过全是sql语句的文件进行导入的命令为：
```
sqlite> .read backup_file.sql
```


## 退出交互命令行
```
sqlite> .exit
```
