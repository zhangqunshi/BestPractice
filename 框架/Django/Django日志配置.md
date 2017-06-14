﻿## Django日志配置
Django的日志在/your_project_name/settings.py文件中配置。具体配置如下：
```python
LOGGING = {
    'version': 1,
    'disable_existing_loggers': False,
    'formatters': {
        'standard': {
            'format': '%(asctime)s %(levelname)-8s %(message)s'
        },
        'detail': {
            'format': '%(asctime)s %(levelname)-8s %(pathname)s[line:%(lineno)d] %(message)s'
        },
    },
    'handlers': {
        'console': {
            'level': 'INFO',
            'class': 'logging.StreamHandler',
            'formatter': 'standard',
        },
        'file': {
            'level': 'INFO',
            'class': 'logging.handlers.RotatingFileHandler',
            'filename': '/var/log/django.log',
            'maxBytes': 1024 * 1024 * 5,  # 5 MB
            'backupCount': 100,
            'formatter': 'detail',
        },
        'app1_file': {
            'level': 'INFO',
            'class': 'logging.handlers.RotatingFileHandler',
            'filename': '/var/log/app1.log',
            'maxBytes': 1024 * 1024 * 5,  # 5 MB
            'backupCount': 100,
            'formatter': 'detail',
        },
        'app2_file': {
            'level': 'INFO',
            'class': 'logging.handlers.RotatingFileHandler',
            'filename': '/var/log/app2.log',
            'maxBytes': 1024 * 1024 * 5,  # 5 MB
            'backupCount': 100,
            'formatter': 'detail',
        },
    },
    'loggers': {
        'django': {
            'handlers': ['console', 'file'],
            'level': 'INFO',
            'propagate': True,
        },
        # 自定义模块日志
        'users': {
            'handlers': ['console', 'file'],
            'level': 'DEBUG',
            'propagate': True,
        },
        'common': {
            'handlers': ['console', 'file'],
            'level': 'DEBUG',
            'propagate': True,
        },
        'myapp': {
            'handlers': ['console', 'file'],
            'level': 'DEBUG',
            'propagate': True,
        },
        'app1': {
            'handlers': ['console', 'app1_file'],
            'level': 'INFO',
            'propagate': True,
        },
        'pushdata': {
            'handlers': ['console', 'app2_file'],
            'level': 'INFO',
            'propagate': True,
        },

    },
}
```

此配置分成三个部分：

 - formatters: 指定输出的格式，被handler使用。
 - handlers： 指定输出到控制台还是文件中，以及输出的方式。被logger引用。
 - loggers： 指定django中的每个模块使用哪个handlers。以及日志输出的级别。

**注意：日志的输出级别是由loggers中的每个模块中level选项定义。如果没有配置，那么默认为warning级别。**


然后在每个模块的views.py中，通过下面代码使用：
```python
import logging
logger = logging.getLogger(__name__)
```
具体的输出部分代码为：

	logger.debug("hello, world")
	logger.info("hello, world")
	logger.error("hello, world")


@完
