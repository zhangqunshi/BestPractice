Python获取当前路径下的配置文件
======

有的时候想读取当前目录下的一个配置文件。其采用的办法是：
```python
import os

# 获取当前路径
curr_dir = os.path.dirname(os.path.realpath(__file__))

# 合成完整路径
config_file = curr_dir + os.sep + "my.conf"
```

其中`__file__`是指当前执行的python文件。

`os.path.realpath()` 返回的是真实地址
`os.path.abspath()` 返回的是软连接地址


参考:
- <http://www.cnblogs.com/strongYaYa/p/5860401.html>
