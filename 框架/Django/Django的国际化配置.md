Django的国际化
====
Django基于python的gettext来实现国际化。其过程为安装gettext工具，然后再代码中调用指定方法，然后用gettext提供的程序来把代码中需要翻译的部分找出来写到一个po的文件中，翻译po中的内容，最后通过gettext工具编译成mo文件。


## 安装gettext

### 在windows上安装gettext
可以自己从下面网址下载：
<http://gnuwin32.sourceforge.net/packages/gettext.htm>

需要下载两个东西：
- gettext-0.14.4-bin.zip
- gettext-0.14.4-dep.zip

### 在Ubuntu上安装gettext
```
apt-get install gettext
```

### 配置环境变量
解压上面的两个下载的zip包。然后把里面的bin目录配置到环境变量的path中。

## 代码中使用gettext
cd ..
```
from django.utils.translation import ugettext as _
from django.http import HttpResponse

def my_view(request):
    output = _("Welcome to my site.")
    return HttpResponse(output)
```


## 生成消息文件
上面的例子中的消息可通过下面的命令生成消息文件
```
django-admin makemessages -l zh_hans
```
注意: 需要在一个app目录下执行此命令。
另外此命令只需要执行一次。以后直接添加消息的翻译。

## 翻译消息
打开locale\zh_hans\LC_MESSAGES下面的django.po文件。
```
msgid "username"
msgstr "用户名"
```
开始对每一个消息进行翻译。


## 编译消息文件
在消息文件翻译之后，还需要把此消息文件变成二进制文件。需要运行下面命令:
```
django-admin compilemessages
```
编译之后会生成django.mo文件。

接下来就可以开始进行测试了。


## 其他事项
注意：决不要在settings文件中导入 django.utils.translation ，因为这个模块本身是依赖于settings，这样做会导致无限循环，而是使用一个“虚构的” gettext() 
```
ugettext = lambda s: s

LANGUAGES = (
    ('de', ugettext('German')),
    ('en', ugettext('English')),
)
```

错误`OSError: No translation files found for default language zh_CN.`需要把settings.py中的LANGUAGE_CODE配置zh-cn修改成zh-hans，就好了

