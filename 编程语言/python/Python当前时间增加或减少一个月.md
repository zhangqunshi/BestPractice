## 问题
今天在之前的代码中发现了一个bug，有个计算当前时间减少一个月的函数，其报出下面的异常信息：

    ValueError: day is out of range for month

看一下代码：
```python
import datatime
def _last_month(now_time):
    last_month = now_time.month - 1
    last_year = now_time.year
    if last_month == 0:
        last_month = 12
        last_year -= 1
    month_time = datetime.datetime(month=last_month, year=last_year, day=now_time.day)
    return month_time
```


----------


## 原因
问题出现在day=now_time.day上。后来想了一下，发现问题原因是**3月30日减少一个月是2月30日，而2月没有30日**，所以就抛出了上面的异常信息。


----------


## 解决办法
对于日期操作，网上的写法都不太一样，而且不确定存在什么bug。日期函数是靠时间来验证的，没准一年以后就出现了（我这个bug是在指定的3月29日以后才能出现，神奇不:D）。
所以我找了一个现有的日期扩展库，希望别人已经踩过大部分坑了。代码如下
```python
import datetime
from dateutil.relativedelta import relativedelta

if __name__ == "__main__":
    print(datetime.date.today() - relativedelta(months=+1))
```

可以看出，主要是使用**relativedelta**类。初始化参数months是月的差异。
安装这个库也很简单，执行命令`pip install python-dateutil`。


----------


## 源码分析
代码在
https://github.com/dateutil/dateutil/blob/master/dateutil/relativedelta.py

判断应该是在354行开始：
```python
if self.months:
            assert 1 <= abs(self.months) <= 12
            month += self.months
            if month > 12:
                year += 1
                month -= 12
            elif month < 1:
                year -= 1
                month += 12
        day = min(calendar.monthrange(year, month)[1],
                  self.day or other.day)
```


参考：
https://dateutil.readthedocs.io/en/stable/
https://github.com/dateutil/dateutil

@完

