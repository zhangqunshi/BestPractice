MySQL大小写问题
=======

## 表名大小写
mysql对于大小写在windows上系统和Linux上系统不一样。Linux默认情况下区分大小写，而在Windows下数据库名、表名、列名、别名都不区分大小写,即使设置lower_case_table_names=0，在查询时还是不会区分大小写。只是在导入导出时会对大小写有区别。原因是各种操作系统的文件系统对大小写实现上有所不同。WINDOWS的文件系统查询时不区分文件大小写。

Linux上大小写问题可以通过配置进行修改。
```
mysql> show variables like 'lower%';
| Variable_name | Value |
+------------------------+-------+
| lower_case_file_system | OFF |
| lower_case_table_names | 0 |
+------------------------+-------+
2 rows in set (0.01 sec)
```
lower_case_file_system：数据库所在的文件系统对文件名大小写敏感度。
- ON 表示大小写不敏感
- OFF 表示敏感


lower_case_table_names：表名大小写敏感度
- 0 表示使用Create语句指定的大小写保存文件
- 1 表示大小写敏感，文件系统以小写保存
- 2 表示使用Create语句指定的大小写保存文件，但MySQL会将之转化为小写 (当Linux设置为2时，错误日志显示[Warning]

修改my.cnf后重启数据库
```
lower_case_table_names=1
```


## 查询时强制区分大小写 (字段数据大小写)
前提： 如果默认为大小写不敏感

### 不区分大小写Sql代码
```
SELECT * FROM table_name WHERE name LIKE 'a%';  
SELECT * FROM table_name WHERE name LIKE 'A%';  
```
其结果是一样的
 
### 区分大小写Sql代码
为了区分'A%'和'a%':
```
SELECT * FROM U WHERE binary name LIKE 'a%';  
SELECT * FROM U WHERE binary name LIKE 'A%';  
```
仅仅多了一个binary，就可以得到不同的结果！
 
当然，如果需要建表时强制区分大小写，可以这么写：
```
create  table  table_name(    
     name varchar (20) binary      
); 
```


### 设置表或行的collation
还有一种方法就是在查询时指定collation
* _bin: 表示的是binary case sensitive collation，也就是说是区分大小写的 
* _cs: case sensitive collation，区分大小写 
* _ci: case insensitive collation，不区分大小写 

例如：
```
create table case_bin_test (word VARCHAR(10)) CHARACTER SET latin1 COLLATE latin1_bin; 
create table case_cs_test (word VARCHAR(10)) CHARACTER SET latin1 COLLATE latin1_general_cs; 
SELECT * FROM case_test WHERE word COLLATE latin1_bin LIKE 'F%'; 
SELECT * FROM case_test WHERE word LIKE 'f%' COLLATE latin1_bin; 
```



-----
参考
- <http://snowolf.iteye.com/blog/1681944>
- 