python模块以及导入出现ImportError: No module named 'xxx'问题
================

环境： CentOS7

# 问题描述
前几天出现ImportError: No module named 'xxx'问题。发现文件夹下面有__init__.py文件。
说明包已经正确。同时把源代码目录加入到了PYTHONPATH环境变量中。但是仍然不好使。没有找到原因，
而且路径检查多遍，确定是正确的。另外由于系统被多个用户公用，所以不能重启系统。

## 解决办法
上网找了一个办法，使用`sys.path.append()`来添加路径。具体代码为：
```
import sys
sys.path.append("/path/your/code")
```

# 其他方法
其实有五种方法：
- 使用PYTHONPATH环境变量
- 将py文件放到site-packages目录下
- 使用pth文件，放到site-packages目录下。（一行一个路径）
- 调用sys.path.append("path")
- 直接把模块文件放到$python_dir/lib目录下


----------
参考: <http://blog.csdn.net/damotiansheng/article/details/43916881>
