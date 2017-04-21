Netbeans8的maven配置文件路径
========

## windows7下的路径

C:\Program Files\NetBeans 8.2\java\maven\conf\settings.xml


## 配置maven阿里云中央仓库

修改maven根目录下的conf文件夹中的setting.xml文件，内容如下：
```
 <mirrors>
    <mirror>
      <id>alimaven</id>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
      <mirrorOf>central</mirrorOf>        
    </mirror>
  </mirrors>
```


顺便表述一下我的观点，Java就是被Maven坑了，这么慢的速度玩啥呀。
建议新项目不要用Maven了。

@完