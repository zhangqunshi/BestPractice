ubuntu安装JDK
===================
# 环境
操作系统：ubuntu 14.04.4 LTS

# 安装方式
安装分为两种方式：
1. 手动下载tar.gz文件，解压并安装
2. 直接通过ubuntu给的命令进行安装

## 第一种方法：手动安装
### 下载JDK
地址为：http://www.oracle.com/technetwork/java/javase/downloads/index.html
```
wget http://download.oracle.com/otn-pub/java/jdk/8u121-b13/e9e7ea248e2c4826b92b3f075a80e441/jdk-8u121-linux-x64.tar.gz
```

### 解压安装
把JDK安装到这个路径：/usr/lib/jvm
如果没有这个目录（第一次当然没有），我们就新建一个目录
```
cd /usr/lib 
sudo mkdir jvm
```
解压文件
```
tar zxvf jdk-8u121-linux-x64.tar.gz -C /usr/lib/jvm
cd /usr/lib/jvm
mv jdk1.8.0_121 jdk8
```
### 配置环境变量
通过`vim /etc/profile`打开文件，并在末尾追加如下内容：

    export JAVA_HOME=/usr/lib/jvm/jdk8
    export JRE_HOME=${JAVA_HOME}/jre
    export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
    export PATH=${JAVA_HOME}/bin:$PATH

保存退出编辑。
使其立刻生效`source /etc/profile`


### 配置默认JDK
由于一些Linux的发行版中已经存在默认的JDK，如OpenJDK等。所以为了使得我们刚才安装好的JDK版本能成为默认的JDK版本，我们还要进行下面的配置。
执行下面的命令：

    sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk8/bin/java 300 
    sudo update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/jdk8/bin/javac 300

注意：如果以上两个命令出现找不到路径问题，只要重启一下计算机在重复上面两行代码就OK了。
执行下面的代码可以看到当前各种JDK版本和配置：

    sudo update-alternatives --config java


## 第二种方法：使用PPA安装(自动安装) --> 有问题
运行下面命令：
```
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer (没有成功,显示E: Unable to locate package oracle-java8-installer)
```

设置 Java 8 环境变量：
    
    sudo apt-get install oracle-java8-set-default

切换为 Java 7 ：`sudo update-java-alternatives -s java-7-oracle`
再切换为 Java 8：`sudo update-java-alternatives -s java-8-oracle`

安装 Java 8 需要接受许可，如果你想自动安装，那么可以在安装之前运行：
    
    echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections

## 测试
打开一个终端，输入命令`java -version`


# 参考
http://www.webupd8.org/2012/09/install-oracle-java-8-in-ubuntu-via-ppa.html
