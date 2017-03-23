ubuntu��װJDK
===================
# ����
����ϵͳ��ubuntu 14.04.4 LTS

# ��װ��ʽ
��װ��Ϊ���ַ�ʽ��
1. �ֶ�����tar.gz�ļ�����ѹ����װ
2. ֱ��ͨ��ubuntu����������а�װ

## ��һ�ַ������ֶ���װ
### ����JDK
��ַΪ��http://www.oracle.com/technetwork/java/javase/downloads/index.html
```
wget http://download.oracle.com/otn-pub/java/jdk/8u121-b13/e9e7ea248e2c4826b92b3f075a80e441/jdk-8u121-linux-x64.tar.gz
```

### ��ѹ��װ
��JDK��װ�����·����/usr/lib/jvm
���û�����Ŀ¼����һ�ε�Ȼû�У������Ǿ��½�һ��Ŀ¼
```
cd /usr/lib 
sudo mkdir jvm
```
��ѹ�ļ�
```
tar zxvf jdk-8u121-linux-x64.tar.gz -C /usr/lib/jvm
cd /usr/lib/jvm
mv jdk1.8.0_121 jdk8
```
### ���û�������
ͨ��`vim /etc/profile`���ļ�������ĩβ׷���������ݣ�

    export JAVA_HOME=/usr/lib/jvm/jdk8
    export JRE_HOME=${JAVA_HOME}/jre
    export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
    export PATH=${JAVA_HOME}/bin:$PATH

�����˳��༭��
ʹ��������Ч`source /etc/profile`


### ����Ĭ��JDK
����һЩLinux�ķ��а����Ѿ�����Ĭ�ϵ�JDK����OpenJDK�ȡ�����Ϊ��ʹ�����ǸղŰ�װ�õ�JDK�汾�ܳ�ΪĬ�ϵ�JDK�汾�����ǻ�Ҫ������������á�
ִ����������

    sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk8/bin/java 300 
    sudo update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/jdk8/bin/javac 300

ע�⣺�������������������Ҳ���·�����⣬ֻҪ����һ�¼�������ظ��������д����OK�ˡ�
ִ������Ĵ�����Կ�����ǰ����JDK�汾�����ã�

    sudo update-alternatives --config java


## �ڶ��ַ�����ʹ��PPA��װ(�Զ���װ) --> ������
�����������
```
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer (û�гɹ�,��ʾE: Unable to locate package oracle-java8-installer)
```

���� Java 8 ����������
    
    sudo apt-get install oracle-java8-set-default

�л�Ϊ Java 7 ��`sudo update-java-alternatives -s java-7-oracle`
���л�Ϊ Java 8��`sudo update-java-alternatives -s java-8-oracle`

��װ Java 8 ��Ҫ������ɣ���������Զ���װ����ô�����ڰ�װ֮ǰ���У�
    
    echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections

## ����
��һ���նˣ���������`java -version`


# �ο�
http://www.webupd8.org/2012/09/install-oracle-java-8-in-ubuntu-via-ppa.html
