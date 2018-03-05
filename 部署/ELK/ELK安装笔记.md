## 安装ELK
首先下载ELK从https://www.elastic.co/downloads/elasticsearch
我下载的是zip版本。
```
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.2.2.zip
```
然后解压缩`unzip elasticsearch-6.2.2.zip`

安装JDK： `sudo apt install default-jdk`

运行Elasticsearch：`bin/elasticsearch`

你会发现在日志有一个警告：
```
max virtual memory areas vm.max_map_count [65530] likely too low, increase to at least [262144] #111
```
这个警告的解决方式是：
编辑`/etc/sysctl.conf` 文件插入一条：
```
vm.max_map_count=262144
```
如果想立马生效使用如下命令：
```
sysctl -w vm.max_map_count=262144
```
参考：https://github.com/docker-library/elasticsearch/issues/111

验证elasticsearch是否安装成功：

curl http://localhost:9200/

会显示如下信息：
```
{
  "name" : "eUv7QJW",
  "cluster_name" : "elasticsearch",
  "cluster_uuid" : "sqib6jeIT1KLWui18wKOKQ",
  "version" : {
  "number" : "6.2.2",
  "build_hash" : "10b1edd",
  "build_date" : "2018-02-16T19:01:30.685723Z",
  "build_snapshot" : false,
  "lucene_version" : "7.2.1",
  "minimum_wire_compatibility_version" : "5.6.0",
  "minimum_index_compatibility_version" : "5.0.0"
  },
  "tagline" : "You Know, for Search"
}
```
## 安装Kibana
- 首先下载Kibana（https://artifacts.elastic.co/downloads/kibana/kibana-6.2.2-linux-x86_64.tar.gz）
- 解压缩下载的文件
- 打开config/kibana.yml文件，修改`elasticsearch.url` 指向elasticsearch实例。由于是在同一台机器上，所以值为:
```
elasticsearch.url: "http://localhost:9200"
server.host: 0.0.0.0
```
上面这个配置实际上在kibana.yml中已经有了，只是把注释的#符号去掉就行。

启动kibana：

bin/kibana

输入的日志为：
```
log [04:54:15.904] [info][status][plugin:kibana@6.2.2] Status changed from uninitialized to green - Ready
  log [04:54:15.942] [info][status][plugin:elasticsearch@6.2.2] Status changed from uninitialized to yellow - Waiting for Elasticsearch
  log [04:54:16.062] [info][status][plugin:timelion@6.2.2] Status changed from uninitialized to green - Ready
  log [04:54:16.067] [info][status][plugin:console@6.2.2] Status changed from uninitialized to green - Ready
  log [04:54:16.071] [info][status][plugin:metrics@6.2.2] Status changed from uninitialized to green - Ready
  log [04:54:16.090] [info][listening] Server running at http://0.0.0.0:5601
  log [04:54:16.132] [info][status][plugin:elasticsearch@6.2.2] Status changed from yellow to green - Ready
```
打开浏览器访问：`http://<your ip address>:5601`。就会看到kibana的首页。

## 安装Logstash
Logstash现在一般都作为log采集和转换发送给ES的中间体，在客户端上可以使用更加轻量级的FileBeat。

下载Logstash:
```
wget https://artifacts.elastic.co/downloads/logstash/logstash-6.2.2.tar.gz
```
解压缩之后，进入config目录，需要配置logstash.yml文件。

