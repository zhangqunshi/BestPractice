MySQL按照月进行统计
=====
今天需要后台提供一个按月统计的API。所以查了一下SQL语句的实现方法。


## 按月统计SQL
```
select 
    date_format(createtime, '%Y-%m') as col_month, 
    username, 
    count(*) 
from
    t_user
group by 
    col_month;
```

可以看到就是把字段createtime，变成了一个年-月的字符串格式，然后再进行group by。
同理，按照年进行统计就是%Y, 按照小时统计就是%H

@完
