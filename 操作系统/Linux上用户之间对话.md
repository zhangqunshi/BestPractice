Linux上用户之间对话
=================
昨天想在CentOS7上与另外一个用户对话，但把命令忘记了，特此记录下来。

## Write命令
write命令是单向发送一条消息给同机器的Linux用户。首先通过`who`命令查看谁在线。

    root     tty7         2017-03-15 14:38 (:0)
    root     pts/20       2017-03-28 20:52 (192.168.0.8)
    root     pts/22       2017-03-29 10:56 (192.168.0.8)
    root     pts/24       2017-03-29 14:12 (192.168.0.8)

第一列为用户名，第二列为终端名，第三列为登录时间。然后输入write命令：
```
    write root pts/20 【回车】
    Hello, World!
    【Ctl+d发送并退出】
```
如果想知道自己是哪个终端，使用命令`who am i`。

## Wall命令
wall命令是群发，会发给同一个机器的所有用户。
```
wall "hello, world!"
```


## talk命令
这个命令相当于聊天室的功能，会打开一个模拟的聊天窗口。而且这个命令不是默认安装的，还需要手动安装一下。格式如下：
```
talk user@hostname [终端名]
```
它的优点是可以实现跨主机进行聊天，也可以同台主机聊天。但是需要启动xinetd服务，并且配置xinetd.d下的ktalk的disable=yes。真是麻烦呀，所以我也没有使用，你们自己折腾吧。^O^ 



@完

-----------
参考
- <http://www.cnblogs.com/yezhenhan/archive/2012/07/10/2584338.html>
- <http://blog.csdn.net/jerry_1126/article/details/52197824>
