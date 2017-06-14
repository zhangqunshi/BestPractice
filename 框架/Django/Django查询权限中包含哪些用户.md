Django查询一个权限中包含哪些用户
==========

Django的Permission对象中没有直接查询相关用户的信息，而都是通过User对象来查询某个用户有哪些权限，例如：
```
user.objects.get(username='admin').user_permissions.values()
```

但是有时候你想根据权限，来查找哪些用户具有此权限，那么下面方法能解决这个问题：
```
from django.contrib.auth.models import Permission, User
p = Permission.objects.get(pk=1)
users = User.objects.filter(Q(groups__permissions=p) | Q(user_permissions=p)).distinct()
```



@完
