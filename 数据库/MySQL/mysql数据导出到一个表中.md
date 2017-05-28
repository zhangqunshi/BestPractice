将Mysql的一张表导出至Excel格式文件
==========

## 导出语句

进入mysql数据库，输入如下sql语句:
```
select id, name, age from tablename into outfile 'd:/data.xls'
```

## secure-file-priv错误

在导出的过程中，有可能遇到一个secure-file-priv错误。这错误是指最近的mysql版本增加的安全机制。
也就是说只允许secure-file-priv指定的目录中的文件被导入和导出。
所以解决的办法有两个：
- 找个这个目录是啥，把文件输出到这个目录中。
- 把secure-file-priv的限制从my.ini中去掉

第一个办法无须改动配置文件重启mysql，所以找出secure-file-priv指定的目录。
```
mysql> show variables where variable_name like '%secure%';
+--------------------------+------------------------------------------------+
| Variable_name            | Value                                          |
+--------------------------+------------------------------------------------+
| require_secure_transport | OFF                                            |
| secure_auth              | ON                                             |
| secure_file_priv         | C:\ProgramData\MySQL\MySQL Server 5.7\Uploads\ |
+--------------------------+------------------------------------------------+
3 rows in set, 1 warning (0.02 sec)
```
通过此sql就查出目录位置。


@完
-----------
参考：
- <http://blog.csdn.net/qq_28384353/article/details/53047716>
- <http://www.hicoogle.com/mysql5-5-database-my-cnf-configuration-file-variable-description.html>
- <http://blog.csdn.net/yongh701/article/details/44060881>

