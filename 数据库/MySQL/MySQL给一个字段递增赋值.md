MySQL给一个字段递增赋值
=====

首先设置一个变量，初始值为0：
```
set @r:=0;
```
然后更新表中对应的ID列：
```
update tablename set id=(@r:=@r+1)
```

如果是插入，那就找一个记录多的表t1
```
set @r:=0;
insert into t select @r:=@r+1 from t1 limit 0, 2000
```

@完
