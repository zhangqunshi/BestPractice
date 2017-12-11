python3编译成pyc文件
======

pyc是编译py之后生成的本地文件。一般当我们想发布系统的时候不想让别人看到源代码，所以要提前生成pyc文件。

现今网上有很多介绍如何生成pyc的文章，但是在python3之后发现其生产__pycache__目录下，而不是与源文件同一目录。

那么如何让python3实现生成的pyc与源代码在同一目录呢？
```
python3 -m compileall -b .
```
加上参数-b就可以了。

所以发布python软件的过程为：
1. 生产pyc文件:  python3 -m compileall -b .
2. 删除py文件:  find . -name "*.py" |xargs rm -rf
3. 删除__pycache__目录:  find . -name "__pycache__" |xargs rm -rf

@完