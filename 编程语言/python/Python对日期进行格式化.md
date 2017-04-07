Python对日期进行格式化
======

把当前时间输出为2017-04-07 19:00:00。进入python交互命令行输入：
```
> import datetime
> currtime = datetime.datetime.now()
> currtime.strftime("%Y-%m-%d %H:%M:%S")

```

@完

参考: <https://docs.python.org/2/library/datetime.html>