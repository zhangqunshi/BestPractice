Django多进程日志文件问题
=========

最近使用Django做一个项目。在部署的时候发现日志文件不能滚动（我使用的是RotatingFileHandler），只有一个日志文件。
查看Log发现一个错误消息：`PermissionError: [WinError 32] 另一个程序正在使用此文件`。

因为我有一些进程需要使用Django的模型层来操作数据库。所以再这些单独的进程中引入了Django：
```
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "myproject.settings")
import django
django.setup()
```

估计这就是主要原因，在使用Django时，Django本身就会使用setting中的log配置来初始化日志模块。而我正好使用的是基于文件的Handler，所以多个进程启动后，都会按照这个方式来初始化日志模块，导致多个进程都在引用此日志文件。在日志文件滚动时，是需要把当前日志文件重命名为xxx.1或者xxx.2。但是由于其他进程也在使用此文件，所以不能修改文件名。

后来再网上查python的多进程日志文件的滚动问题，发现大家都有类似问题。看来这是python自身的一个常见问题，但是还没有什么标准的解决方法，有的是采用多进程共享queue的方式，多个进程把日志往queue中写，然后一个线程负责把queue中的日志消息往文件中写。但多进程共享queue在linux上和windows上表现还有差异，真是越来越烦。具体讨论请参考这个文章：
<https://stackoverflow.com/questions/641420/how-should-i-log-while-using-multiprocessing-in-python/894284>

代码如下：
```
import logging
import multiprocessing
import threading
import time
from logging.handlers import RotatingFileHandler


class MultiProcessingLogHandler(logging.Handler):
    def __init__(self, filename, mode='a', maxBytes=0, backupCount=0, encoding=None, delay=False):
        logging.Handler.__init__(self)

        self._handler = RotatingFileHandler(filename, mode, maxBytes, backupCount, encoding, delay)
        self.queue = multiprocessing.Queue(-1)

        t = threading.Thread(target=self.receive)
        t.daemon = True
        t.start()

    def setFormatter(self, fmt):
        logging.Handler.setFormatter(self, fmt)
        self._handler.setFormatter(fmt)

    def receive(self):
        while True:
            try:
                record = self.queue.get()
                self._handler.emit(record)
            except (KeyboardInterrupt, SystemExit):
                raise
            except EOFError:
                break
            except:
                traceback.print_exc(file=sys.stderr)

    def send(self, s):
        self.queue.put_nowait(s)

    def _format_record(self, record):
        # ensure that exc_info and args
        # have been stringified.  Removes any chance of
        # unpickleable things inside and possibly reduces
        # message size sent over the pipe
        if record.args:
            record.msg = record.msg % record.args
            record.args = None
        if record.exc_info:
            dummy = self.format(record)
            record.exc_info = None

        return record

    def emit(self, record):
        try:
            s = self._format_record(record)
            self.send(s)
        except (KeyboardInterrupt, SystemExit):
            raise
        except:
            self.handleError(record)

    def close(self):
        self._handler.close()
        logging.Handler.close(self)
```
然后把这个类写在settings.py中的handler的class中。
我测试的时候还是没有好使，不知道什么原因:(  我宝贵的时间呀！

使用zeromq的方案倒是吸引了我，因为我项目中正好使用zeromq，比较简单可靠。说白了就是把日志发给一个socket，然后socket服务端读取消息，并写入到日志文件中。
这个方案涉及到要自定义一个Handler，这就是查查python如何自定义handler（zeromq好像已经提供了一个PUBHandler: <http://pyzmq.readthedocs.io/en/latest/api/zmq.log.handlers.html#zmq.log.handlers.PUBHandler>）。

关于如何自定义handler，这里有篇文件讲了：
<http://www.cnblogs.com/shizioo/p/python_logging_handler_custom.html#undefined>


```
formatters = {
    logging.DEBUG: logging.Formatter("[%(name)s] %(message)s"),
    logging.INFO: logging.Formatter("[%(name)s] %(message)s"),
    logging.WARN: logging.Formatter("[%(name)s] %(message)s"),
    logging.ERROR: logging.Formatter("[%(name)s] %(message)s"),
    logging.CRITICAL: logging.Formatter("[%(name)s] %(message)s")
}

# This one will be used by publishing processes
class PUBLogger:
    def __init__(self, host, port=config.PUBSUB_LOGGER_PORT):
        self._logger = logging.getLogger(__name__)
        self._logger.setLevel(logging.DEBUG)
        self.ctx = zmq.Context()
        self.pub = self.ctx.socket(zmq.PUB)
        self.pub.connect('tcp://{0}:{1}'.format(socket.gethostbyname(host), port))
        self._handler = PUBHandler(self.pub)
        self._handler.formatters = formatters
        self._logger.addHandler(self._handler)

    @property
    def logger(self):
        return self._logger

# This one will be used by listener process
class SUBLogger:
    def __init__(self, ip, output_dir="", port=config.PUBSUB_LOGGER_PORT):
        self.output_dir = output_dir
        self._logger = logging.getLogger()
        self._logger.setLevel(logging.DEBUG)

        self.ctx = zmq.Context()
        self._sub = self.ctx.socket(zmq.SUB)
        self._sub.bind('tcp://*:{1}'.format(ip, port))
        self._sub.setsockopt(zmq.SUBSCRIBE, "")

        handler = handlers.RotatingFileHandler(os.path.join(output_dir,                 "client_debug.log"), "w", 100 * 1024 * 1024, 10)
        handler.setLevel(logging.DEBUG)
        formatter = logging.Formatter("%(asctime)s;%(levelname)s - %(message)s")
        handler.setFormatter(formatter)
        self._logger.addHandler(handler)

  @property
  def sub(self):
      return self._sub

  @property
  def logger(self):
      return self._logger

#  And that's the way we actually run things:

# Listener process will forever listen on SUB socket for incoming messages
def run_sub_logger(ip, event):
    sub_logger = SUBLogger(ip)
    while not event.is_set():
        try:
            topic, message = sub_logger.sub.recv_multipart(flags=zmq.NOBLOCK)
            log_msg = getattr(logging, topic.lower())
            log_msg(message)
        except zmq.ZMQError as zmq_error:
            if zmq_error.errno == zmq.EAGAIN:
                pass


# Publisher processes loggers should be initialized as follows:

class Publisher:
    def __init__(self, stop_event, proc_id):
        self.stop_event = stop_event
        self.proc_id = proc_id
        self._logger = pub_logger.PUBLogger('127.0.0.1').logger

     def run(self):
         self._logger.info("{0} - Sending message".format(proc_id))

def run_worker(event, proc_id):
    worker = Publisher(event, proc_id)
    worker.run()

# Starting subscriber process so we won't loose publisher's messages
sub_logger_process = Process(target=run_sub_logger,
                                 args=('127.0.0.1'), stop_event,))
sub_logger_process.start()

#Starting publisher processes
for i in range(MAX_WORKERS_PER_CLIENT):
    processes.append(Process(target=run_worker,
                                 args=(stop_event, i,)))
for p in processes:
    p.start()
```
上面是别人写的关于zeromq方式多进程日志的解决方法。
上面看起来有些复杂，最后使用的是zeromq自己提供的PUBHandler。同时自己再写一个接收端，并使用Python自身的logging模块往文件中记录。

PUBHandler使用的是PUB方式:
```
self.ctx = context or zmq.Context()
self.socket = self.ctx.socket(zmq.PUB)
self.socket.bind(interface_or_socket)
```

其中`inproc://log`方式不行，因为这个是线程间通信，对于并不是进程间通信（参考：<http://www.cnblogs.com/fengbohello/p/4328772.html>）。所以想使用进程间通信需要用`ipc://address`。但是看到此方式只在UNIX系统上完全实现了，心又凉一半，你让我再windows上怎么测试。看来只能换成tcp方式了。
具体实现请看这篇文章：<http://seewind.blog.51cto.com/249547/288343>。

使用PUB是不行的，因为多个进程调用此处会导致`Address in use`的错误。哎~~~，继续调查，最后决定使用PUSH和PULL模式。

最后的代码如下：
```
# -*- coding:utf-8 -*-
import logging

import zmq
from zmq.utils.strtypes import cast_bytes

default_formatter = logging.Formatter('%(asctime)s %(levelname)-8s %(pathname)s[line:%(lineno)d] %(message)s')


class PUSHHandler(logging.Handler):
    """
    改造了zeromq的PUBHandler，而使用PUSH(client), PULL(server)模式
    """
    socket = None

    formatters = {
        logging.DEBUG: default_formatter,
        logging.INFO: default_formatter,
        logging.WARN: default_formatter,
        logging.ERROR: default_formatter,
        logging.CRITICAL: default_formatter
    }

    def __init__(self, interface_or_socket, context=None):
        logging.Handler.__init__(self)
        if isinstance(interface_or_socket, zmq.Socket):
            self.socket = interface_or_socket
            self.ctx = self.socket.context
        else:
            self.ctx = context or zmq.Context()
            self.socket = self.ctx.socket(zmq.PUSH)
            self.socket.connect(interface_or_socket)

    def format(self, record):
        """Format a record."""
        return self.formatters[record.levelno].format(record)

    def emit(self, record):
        """Emit a log message on my socket."""
        try:
            bmsg = cast_bytes(self.format(record))
        except Exception:
            self.handleError(record)
            return

        self.socket.send(bmsg)

```
在django的settings.py中使用此handler:
```
    'handlers': {
        'console': {
            'level': 'INFO',
            'class': 'logging.StreamHandler',
            'formatter': 'detail',
        },
        'file': {
            'level': 'INFO',
            'class': 'common.zeromq_loghandler.PUSHHandler',
            'interface_or_socket': 'tcp://localhost:%s' % LOG_LISTEN_PORT,
        },
    },
```

最后log的服务端代码如下：
```
# -*- coding:utf-8 -*-
import logging

import zmq

from common.message_log import get_logger
from myproject.settings import LOG_BASE_DIR, LOG_LISTEN_PORT

simple_formatter = logging.Formatter('%(message)s')

logger = get_logger(LOG_BASE_DIR + '/django.log', formatter=simple_formatter)


class LoggingServer(object):
    """
    日志接收服务端 (为了解决多进程访问相同的日志文件问题)
    """

    def run(self):
        logger.info("start LoggingServer")
        context = zmq.Context()
        socket = context.socket(zmq.PULL)
        socket.bind("tcp://*:%s" % LOG_LISTEN_PORT)
        logger.info("Listen on %s" % LOG_LISTEN_PORT)

        while True:
            try:
                msg = socket.recv_string()
                logger.info(msg)
            except Exception as e:
                logger.error("Error logging server: %s" % e)


def main():
    try:
        server = LoggingServer()
        server.run()
    except Exception as e:
        logger.error('Error: %s' % e)


if __name__ == "__main__":
    main()

```
就这么简单的一个日志问题，花费了我一天时间，非常不开心 :-(


关于开源的python第三方logging模块，没敢用，在Github上有structlog, logzero, python-logstash。不知道哪个好，或者有什么坑，大家试试然后告诉我 :)



------------
参考：
- <https://stackoverflow.com/questions/26830918/django-logging-rotating-files-not-working#>
- <https://stackoverflow.com/questions/32805594/file-handling-in-python-being-used-by-another-process>
- <https://docs.python.org/3/howto/logging-cookbook.html#logging-to-a-single-file-from-multiple-processes>
- <https://stackoverflow.com/questions/641420/how-should-i-log-while-using-multiprocessing-in-python>
- <http://www.cnblogs.com/restran/p/4743840.html#undefined>
- <https://stackoverflow.com/questions/32564472/multiple-processors-logging-to-same-rotate-file>
- <https://stackoverflow.com/questions/641420/how-should-i-log-while-using-multiprocessing-in-python/894284>
- <http://python.jobbole.com/87300/>
- <http://blog.chinaunix.net/uid-26983585-id-3858224.html?/1200.shtml>






