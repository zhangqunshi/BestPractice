Derby的jar说明
====

Derby的下载后，解压发现lib中有很多jar包，下面说明一下每个jar包的用途：

### 引擎库
derby.jar是引擎库，必须的
For embedded databases.
You always need this library for embedded environments. For client/server environments, you only need this library on the server.

### 工具库
1. derbytools.jar: Required for running all the Derby tools (such as ij, dblook, and import/export).
2. derbyrun.jar: Executable jar file that can be used to start the Derby tools.

### 网络服务端库
如果是通过网络连接Derby（非嵌入方式），那么需要这个jar。
derbynet.jar: Required to start the Derby Network Server.


### 网络客户端库
derbyclient.jar:	Required to use the Derby network client driver.


### 本地语言库
1. derbyLocale_cs.jar	Required to provide translated messages for the Czech locale.
1. derbyLocale_de_DE.jar	Required to provide translated messages for the German locale.
1. derbyLocale_es.jar	Required to provide translated messages for the Spanish locale.
1. derbyLocale_fr.jar	Required to provide translated messages for the French locale.
1. derbyLocale_hu.jar	Required to provide translated messages for the Hungarian locale.
1. derbyLocale_it.jar	Required to provide translated messages for the Italian locale.
1. derbyLocale_ja_JP.jar	Required to provide translated messages for the Japanese locale.
1. derbyLocale_ko_KR.jar	Required to provide translated messages for the Korean locale.
1. derbyLocale_pl.jar	Required to provide translated messages for the Polish locale.
1. derbyLocale_pt_BR.jar	Required to provide translated messages for the Brazilian Portuguese locale.
1. derbyLocale_ru.jar	Required to provide translated messages for the Russian locale.
1. derbyLocale_zh_CN.jar	Required to provide translated messages for the Simplified Chinese locale.
1. derbyLocale_zh_TW.jar	Required to provide translated messages for the Traditional Chinese locale.


------
参考：
<https://builds.apache.org/job/Derby-docs/lastSuccessfulBuild/artifact/trunk/out/getstart/index.html>