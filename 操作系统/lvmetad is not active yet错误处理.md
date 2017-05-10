Lvmetad is not active yet错误处理
==========

## 问题现象
今天早上把之前的服务器关机换了一个位置，之后启动后出现错误信息：
```
lvmetad is not active yet; using direct activation during sysinit
```

## 原因
这不是一个严重错误，一般情况下LVM会扫描分区建立缓存，延迟几秒后就会继续。但是如果很长时间都不能进入的话，可以输入Alt+F2进入第二个终端窗口，正常进行操作。等过了一两个小时后，扫描结束，在通过Alt+F1切换回第一终端窗口，就应该正常了。


-----
参考：
1. <https://askubuntu.com/questions/767140/lvmetad-is-not-active-yet>
2. <https://askubuntu.com/questions/865869/lvmetad-is-blocking-boot-process?noredirect=1&lq=1>