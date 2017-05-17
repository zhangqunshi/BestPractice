Python机器学习库sklearn的安装
==================
scikit-learn是Python的一个开源机器学习模块，它建立在NumPy，SciPy和matplotlib模块之上能够为用户提供各种机器学习算法接口，可以让用户简单、高效地进行数据挖掘和数据分析。

# Ubuntu14.04系统上安装

## 安装numpy
首选需要安装numpy:
```
pip install numpy
```

## 安装scipy
```
$ sudo apt-get install libblas-dev liblapack-dev libatlas-base-dev gfortran
$ sudo pip install scipy
```

## 安装scikit-learn
```
$ sudo pip install sklearn
```
最后输出Successfully installed scikit-learn-0.18.1 sklearn-0.0

## 测试
在 terminal 里面输入
```
pip list
```
这个会列出sklearn这一项,应该就是大功告成了!

----------------------

# CentOS7系统上安装
```
yum -y install gcc gcc-c++  (如果已经安装gcc跳过此步骤)
yum -y install numpy python-devel scipy
```

----------------------

# Windows7系统上安装
首先下载whl文件

- numpy文件 < http://www.lfd.uci.edu/~gohlke/pythonlibs/tugh5y6k/numpy-1.12.0+mkl-cp35-cp35m-win_amd64.whl>
- scipy文件 <http://www.lfd.uci.edu/~gohlke/pythonlibs/#scipy>

然后执行
```
pip install numpy-1.12.0+mkl-cp35-cp35m-win_amd64.whl
pip install <scipy-xxx.whl>
pip install sklearn
```

-----------------
参考
- http://www.hareric.com/2016/05/22/scikit-learn%E7%9A%84%E5%AE%89%E8%A3%85%E5%92%8C%E5%9F%BA%E6%9C%AC%E4%BD%BF%E7%94%A8%E6%95%99%E7%A8%8B/
