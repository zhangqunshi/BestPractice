django的模型使用AND和OR进行查询
========

有的时候查询需要多个条件，例如下面：

```
select * from MyEntity where (name='kris' and age=100) or (name='kris2' and age=50)
```

使用模型来查询：

```
from django.db.models import Q

MyEntity.objects.filter(Q(name='kris', age=100) | Q(name='kris2', age=50))
```
