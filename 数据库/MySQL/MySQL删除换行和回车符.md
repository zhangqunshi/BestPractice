MySQL删除换行和回车符
=======

前几天在查看数据的时候，发现竟然有`\n\r`回车和换行符号。而这种数据在正常UI界面上发现不了，而是导出成SQL语句时看到的。所以就想到统一删除这个字段所有回车和换行字符。

```
UPDATE tablename SET colname = REPLACE(REPLACE(colname, CHAR(10), ""), CHAR(13), "");
```

上面的SQL语句中：
- char(10):  换行符
- char(13):  回车符

@完

------
参考：<http://blog.csdn.net/gyanp/article/details/7109314>