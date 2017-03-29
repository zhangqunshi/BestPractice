# 介绍
Python本身带有logging模块，其默认支持直接输出到控制台（屏幕），或者通过配置输出到文件中。同时支持TCP、HTTP、GET/POST、SMTP、Socket等协议，将日志信息发送到网络等等。


----------
## Python日志级别
日志级别大小关系为：CRITICAL > ERROR > WARNING > INFO > DEBUG > NOTSET，当然也可以自己定义日志级别。

## 直接使用logging
没有配置logging时，日志会直接输出到控制台
```python
import logging
if __name__ == "__main__":
	logging.debug("hello debug")
	logging.info("hello info")
	logging.warning("hello warning")
	logging.error("hello error")
```
输出结果：

	WARNING:root:hello warning
	ERROR:root:hello error

是不是少了上面两个？ 因为默认情况下，logging的级别为warning.

----------
## 配置logging
通过logging.basicConfig函数做相关配置:
```python
import logging

logging.basicConfig(level=logging.DEBUG,
    format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',)

if __name__ == "__main__":
	logging.debug("hello debug")
	logging.info("hello info")
	logging.warning("hello warning")
	logging.error("hello error")
```

输出为：

	2017-03-28 12:22:55,052 test.py[line:8] DEBUG hello debug
	2017-03-28 12:22:55,053 test.py[line:9] INFO hello info
	2017-03-28 12:22:55,053 test.py[line:10] WARNING hello warning
	2017-03-28 12:22:55,054 test.py[line:11] ERROR hello error

可以指定日期格式。python中时间日期格式化符号：
	>%y 两位数的年份表示（00-99）
	%Y 四位数的年份表示（000-9999）
	%m 月份（01-12）
	%d 月内中的一天（0-31）
	%H 24小时制小时数（0-23）
	%I 12小时制小时数（01-12）
	%M 分钟数（00=59）
	%S 秒（00-59）
	%a 本地简化星期名称
	%A 本地完整星期名称
	%b 本地简化的月份名称
	%B 本地完整的月份名称
	%c 本地相应的日期表示和时间表示
	%j 年内的一天（001-366）
	%p 本地A.M.或P.M.的等价符
	%U 一年中的星期数（00-53）星期天为星期的开始
	%w 星期（0-6），星期天为星期的开始
	%W 一年中的星期数（00-53）星期一为星期的开始
	%x 本地相应的日期表示
	%X 本地相应的时间表示
	%Z 当前时区的名称
	%% %号本身

代码修改如下：
```python
import logging

logging.basicConfig(level=logging.DEBUG,
    format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',
    datefmt='%Y-%m-%d %A %H:%M:%S')

if __name__ == "__main__":
	logging.debug("hello debug");
	logging.info("hello info");
	logging.warning("hello warning");
	logging.error("hello error");
```

----------
### 输出到文件中

通过logging.basicConfig来配置输出文件路径：
```python
import logging

logging.basicConfig(level=logging.DEBUG,
    format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',
    datefmt='%Y-%m-%d %A %H:%M:%S',
    filename='python.log',
    filemode='w')

if __name__ == "__main__":
	logging.debug("hello debug");
	logging.info("hello info");
	logging.warning("hello warning");
	logging.error("hello error");
```
输出结果:

	2017-03-28 Tuesday 12:33:29 test.py[line:10] DEBUG hello debug
	2017-03-28 Tuesday 12:33:29 test.py[line:11] INFO hello info
	2017-03-28 Tuesday 12:33:29 test.py[line:12] WARNING hello warning
	2017-03-28 Tuesday 12:33:29 test.py[line:13] ERROR hello error

----------
###logging.basicConfig函数各参数
- filename: 指定日志文件名
- filemode: 和file函数意义相同，指定日志文件的打开模式，'w'或'a'
- format: 指定输出的格式和内容，format可以输出很多有用信息，如上例所示:
	 >%(levelno)s: 打印日志级别的数值
	 %(levelname)s: 打印日志级别名称
	 %(pathname)s: 打印当前执行程序的路径，其实就是sys.argv[0]
	 %(filename)s: 打印当前执行程序名
	 %(funcName)s: 打印日志的当前函数
	 %(lineno)d: 打印日志的当前行号
	 %(asctime)s: 打印日志的时间
	 %(thread)d: 打印线程ID
	 %(threadName)s: 打印线程名称
	 %(process)d: 打印进程ID
	 %(message)s: 打印日志信息

- datefmt: 指定时间格式，同time.strftime()
- level: 设置日志级别，默认为logging.WARNING
- stream: 指定将日志的输出流，可以指定输出到sys.stderr，sys.stdout或者文件，默认输出到sys.stderr，当stream和filename同时指定时，stream被忽略。

----------
##  同时输出到文件和控制台中
通过handler来让日志可以即输出到文件中又输出到控制台上。

```python
import logging

logging.basicConfig(level=logging.DEBUG,
    format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',
    datefmt='%Y-%m-%d %A %H:%M:%S',
    filename='python.log',
    filemode='w')

console = logging.StreamHandler()
console.setLevel(logging.INFO)
formatter = logging.Formatter('%(levelname)-8s %(message)s')
console.setFormatter(formatter)
logging.getLogger('').addHandler(console)

if __name__ == "__main__":
	logging.debug("hello debug");
	logging.info("hello info");
	logging.warning("hello warning");
	logging.error("hello error");
```

控制台输出内容：

	INFO     hello info
	WARNING  hello warning
	ERROR    hello error

文件中输出内容：

	2017-03-28 Tuesday 12:38:19 test.py[line:16] DEBUG hello debug
	2017-03-28 Tuesday 12:38:19 test.py[line:17] INFO hello info
	2017-03-28 Tuesday 12:38:19 test.py[line:18] WARNING hello warning
	2017-03-28 Tuesday 12:38:19 test.py[line:19] ERROR hello error

----------
##  滚动文件日志
当日志一直向一个文件中输出，会导致文件太大。所以有时候希望日志文件滚动生成多个文件。
```python
import logging
from logging.handlers import RotatingFileHandler

logging.basicConfig(level=logging.DEBUG,
    format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',
    datefmt='%Y-%m-%d %A %H:%M:%S')

rfhandler = RotatingFileHandler('python.log', 
    maxBytes=10*1024*1024,
    backupCount=5)
rfhandler.setLevel(logging.INFO)
formatter = logging.Formatter('%(asctime)s %(levelname)-8s %(message)s')
rfhandler.setFormatter(formatter)
logging.getLogger('').addHandler(rfhandler)

if __name__ == "__main__":
	logging.debug("hello debug");
	logging.info("hello info");
	logging.warning("hello warning");
	logging.error("hello error");
```
当文件输出超过10*1024*1024=10M大小时，就会生成一个新的日志文件。

RotatingFileHandler的参数： 

 - maxBytes 最大文件大小，单位字节，0代表无限大。
 - backupCount 保留的备份个数。
 

----------
## logging的Handler方式
logging的包括如下几种handler方式：

- logging.StreamHandler: 日志输出到流，可以是sys.stderr、sys.stdout或者文件
- logging.FileHandler: 日志输出到文件
- logging.handlers.RotatingFileHandler: 基于文件日志大小滚动
- logging.handlers.TimedRotatingFileHandler: 基于时间进行滚动
- logging.handlers.BaseRotatingHandler
- logging.handlers.SocketHandler: 远程输出日志到TCP/IP sockets
- logging.handlers.DatagramHandler:  远程输出日志到UDP sockets
- logging.handlers.SMTPHandler:  远程输出日志到邮件地址
- logging.handlers.SysLogHandler: 日志输出到syslog
- logging.handlers.NTEventLogHandler: 远程输出日志到Windows NT/2000/XP的事件日志
- logging.handlers.MemoryHandler: 日志输出到内存中的制定buffer
- logging.handlers.HTTPHandler: 通过"GET"或"POST"远程输出到HTTP服务器

由于StreamHandler和FileHandler是常用的日志处理方式，所以直接包含在logging模块中，而其他方式则包含在logging.handlers模块中，

----------
## 使用logging配置文件
还有一种方式是不在代码中对logging进行配置，而是写到配置文件中。
```
#logger.conf
############################
[loggers]
keys=root,example01,example02

[logger_root]
level=DEBUG
handlers=hand01,hand02

[logger_example01]
handlers=hand01,hand02
qualname=example01
propagate=0

[logger_example02]
handlers=hand01,hand03
qualname=example02
propagate=0
############################
[handlers]
keys=hand01,hand02,hand03

[handler_hand01]
class=StreamHandler
level=INFO
formatter=form02
args=(sys.stderr,)

[handler_hand02]
class=FileHandler
level=DEBUG
formatter=form01
args=('python.log', 'a')

[handler_hand03]
class=handlers.RotatingFileHandler
level=INFO
formatter=form02
args=('python.log', 'a', 10*1024*1024, 5)
############################
[formatters]
keys=form01,form02

[formatter_form01]
format=%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s
datefmt=%a, %d %b %Y %H:%M:%S

[formatter_form02]
format=%(name)-12s: %(levelname)-8s %(message)s
datefmt=
```

在python使用如下：
```python
import logging
import logging.config

logging.config.fileConfig("logger.conf")
logger = logging.getLogger("example01")

if __name__ == "__main__":
	logger.debug("hello debug");
	logger.info("hello info");
	logger.warning("hello warning");
	logger.error("hello error");
```

----------
## Formatter配置
日志的输出格式format:

	%(levelno)s: 打印日志级别的数值
	%(levelname)s: 打印日志级别名称
	%(pathname)s: 打印当前执行程序的路径，其实就是sys.argv[0]
	%(filename)s: 打印当前执行程序名
	%(funcName)s: 打印日志的当前函数
	%(lineno)d: 打印日志的当前行号
	%(asctime)s: 打印日志的时间
	%(thread)d: 打印线程ID
	%(threadName)s: 打印线程名称
	%(process)d: 打印进程ID
	%(message)s: 打印日志信息

工作中给的常用格式是：

	format='%(asctime)s - %(filename)s[line:%(lineno)d] - %(levelname)s: %(message)s'

这个格式可以输出日志的打印时间，是哪个模块输出的，输出的日志级别是什么，以及输入的日志内容。


@待续


-------------------------
参考：
- <http://blog.csdn.net/yatere/article/details/6655445>
- <http://blog.csdn.net/z_johnny/article/details/50812878>
- <http://blog.csdn.net/liuchunming033/article/details/39080457>
