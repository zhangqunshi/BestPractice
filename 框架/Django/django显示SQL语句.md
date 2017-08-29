django显示SQL语句
========

有时候我们使用模型查询数据，但是并不知道具体执行的SQL语句到底对不对。那么可以通过下面的方法打印出具体执行的SQL语句。这样有助于调试：
```
queryset = MyModel.objects.all()
queryset.query.__str__()
```

参考：
---------
<https://stackoverflow.com/questions/3748295/getting-the-sql-from-a-django-queryset>

