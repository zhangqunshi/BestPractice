Django通过数据库生成模型类
=====

在Django的项目目录下输入命令：
```
python manage.py inspectdb
或
python manage.py inspectdb > models.py
```

生成好的model类中，对于双主键的表，类中是没有主键字段对应的，所以需要修改表结构，增加一个id列（非主键），来兼容Django。

