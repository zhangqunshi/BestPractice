MySQL查询近一个月的数据
=====

近一个月统计SQL
```
select 
    user_id,
    user_name,
    createtime
from
    t_user
where
    DATE_SUB(CURDATE(), INTERVAL 1 MONTH) <= date(createtime);
```

同理，近一个星期为: `INTERVAL 7 DAY`。

@完
