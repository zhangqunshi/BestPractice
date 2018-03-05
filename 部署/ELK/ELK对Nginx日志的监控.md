ELK对Nginx日志的监控
==============

首先安装EK，具体安装步骤可以参考我之前写的ELK学习笔记。

## 安装GeoIP和UserAgent插件
```
bin/elasticsearch-plugin install ingest-geoip
bin/elasticsearch-plugin install ingest-user-agent
```

输出日志：
```
-> Downloading ingest-geoip from elastic
[=================================================] 100%   
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@     WARNING: plugin requires additional permissions     @
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
* java.lang.RuntimePermission accessDeclaredMembers
* java.lang.reflect.ReflectPermission suppressAccessChecks
See http://docs.oracle.com/javase/8/docs/technotes/guides/security/permissions.html
for descriptions of what these permissions allow and the associated risks.

Continue with installation? [y/N]y 
-> Installed ingest-geoip


-> Downloading ingest-user-agent from elastic
[=================================================] 100%   
-> Installed ingest-user-agent

```

## 下载FileBeat

如果是第一次使用FileBeat，请参考文档：[https://www.elastic.co/guide/en/beats/filebeat/6.x/filebeat-getting-started.html]
```
curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-6.2.2-darwin-x86_64.tar.gz
tar xzvf filebeat-6.2.2-darwin-x86_64.tar.gz
cd filebeat-6.2.2-darwin-x86_64/
```

## 配置FileBeat
修改filebeat.yml文件：
```
output.elasticsearch:
  hosts: ["<es_url>"]
  username: "elastic"
  password: "<password>"
setup.kibana:
  host: "<kibana_url>"
```
其实使用默认配置就行。

## 启动nginx配置
输入：
```
./filebeat modules enable nginx
```

遇到如下错误：
```
-bash: ./filebeat: cannot execute binary file: Exec format error
```

