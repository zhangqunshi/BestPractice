django过滤日期字段
========

有时候要根据日期字段进行判断和查询，对于django的写法如下

```
YourModelName.objects.filter(createtime__gt=datetime.now())  # 大于当前时间

YourModelName.objects.filter(createtime__gte=datetime.now())  # 大于等于当前时间

YourModelName.objects.filter(createtime__lt=datetime.now())  # 小于当前时间

YourModelName.objects.filter(createtime__lte=datetime.now())  # 小于等于当前时间

```


