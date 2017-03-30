# 介绍
pip命令是python用来管理包的工具。也就是使用这个pip命令来安装其他第三方python包。

# 安装pip

今天在CentOS7上安装Django，想通过pip命令进行安装，但是发现没有pip命令。所以就需要先安装pip。

## 下载get-pip.py文件

在CentOS上直接通过下面的命令下载`get-pip.py`文件。

```sh
    wget https://bootstrap.pypa.io/get-pip.py
```

这个文件竟然有1595408 (1.5M)大小。好大呀！

## 开始安装pip

在get-pip.py文件所在的目录下，运行下面命令
```sh
    python ./get-pip.py
```

之后就可以开始安装Django了。

@END