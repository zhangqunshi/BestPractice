Python生成pyc文件
=====================
pyc文件是py文件编译后生成的字节码文件(byte code)。pyc文件经过python解释器最终会生成机器码运行。所以pyc文件是可以跨平台部署的，类似Java的.class文件。一般py文件改变后，都会重新生成pyc文件。

为什么要手动提前生成pyc文件呢，主要是不想把源代码暴露出来。

## 生成单个pyc文件
对于py文件，可以执行下面命令来生成pyc文件。
```
python -m foo.py
```
另外一种方式是通过代码来生成pyc文件。
```
import py_compile
py_compile.compile('/path/to/foo.py')
```

## 批量生成pyc文件
针对一个目录下所有的py文件进行编译。python提供了一个模块叫compileall，具体请看下面代码：
```
import compileall
compileall.compile_dir(r'/path')
```
这个函数的格式如下：

    compile_dir(dir[, maxlevels[, ddir[, force[, rx[, quiet]]]]])

参数含义：
- maxlevels: 递归编译的层数
- ddir: If ddir is given, it is prepended to the path to each file being compiled for use in compilation time tracebacks, and is also compiled in to the byte-code file, where it will be used in tracebacks and other messages in cases where the source file does not exist at the time the byte-code file is executed. (谁能翻译一下( ⊙o⊙?)不懂)
- force: 如果True，不论是是否有pyc，都重新编译
- rx: 一个正则表达式，排除掉不想要的目录
- quiet：如果为True，则编译不会在标准输出中打印信息

命令行为：
```
python -m compileall <dir>
```

@完

------------
参考：
- <http://www.cnblogs.com/dkblog/archive/2009/04/16/1980757.html>
- <https://docs.python.org/2/library/compileall.html>
- <https://docs.python.org/3/library/compileall.html>
- <http://blog.csdn.net/loveyilili/article/details/12567729>
