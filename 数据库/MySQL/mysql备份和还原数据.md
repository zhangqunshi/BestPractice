MySQL备份和还原数据
=====

## 导出整个数据库
```
mysqldump -uroot -p database_name > db_backup.sql
```

## 导入整个数据库
```
mysql -uroot -p database_name < db_backup.sql
```


## 导出一个表
```
mysqldump -uroot -p database_name table_name > table_backup.sql
```
上面会导出表结构和数据。

## 导入一个表
```
mysql -uroot -p database_name < table_backup.sql
```

## 仅仅导出数据库结构
```
mysqldump -uroot -p -d database_name > db_structure_backup.sql
```

## 仅仅导出表结构
```
mysqldump -uroot -p -d database_name table_name > table_structure_backup.sql
```


## 仅仅导出数据库中数据
```
mysqldump -uroot -p -t database_name > db_data_backup.sql
```

## 仅仅导出表中数据
```
mysqldump -uroot -p -t database_name table_name > table_data_backup.sql
```


上面的备份，如果想还原，都可以使用`mysql -uroot -p database_name < backup.sql`


@完

参考
-----
- <http://www.cnblogs.com/kissdodog/p/4174421.html>
