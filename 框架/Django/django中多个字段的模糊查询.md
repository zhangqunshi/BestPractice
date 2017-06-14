django中多个字段的模糊查询
========


使用`Entity.objects.filter(name_contains='kris').filter(address='beijing')`

这个方法是指名字包含kris，并且地址包含beijing的记录。

如果是不区分大小写，那么使用icontains替换contains.

如果要改成或的话，可以使用如下形式:

```
Entity.objects.filter(Q(name_icontains='kris') | Q(address_icontains='beijing'))
```