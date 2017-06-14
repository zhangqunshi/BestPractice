Django开发步骤
========

Django框架每次开发的初始化的套路都基本一样，这里记录一下。

## 安装Django
首先安装Python软件，上python官网下载对应的安装包。接下来就是安装Django：
```
pip install django
```
最新版本的Python都基本自带pip命令，所以直接就可以安装Django。

## 初始化项目
为新项目建立一个目录，在cmd下面运行命令：
```
django-admin startproject <project_name>
```

## 建立数据库
安装MySQL后，进入mysql并运行：
```
create database <dbname> character set utf8;
```
之后还有安装python的mysql驱动。

## 配置数据库连接
进入django建立好的工程目录中，有个settings.py文件，编辑此文件中的DATABASES部分。
```
DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.mysql',
        'NAME': 'dbname',
        'USER': 'root',
        'PASSWORD': '123456',
        'HOST': 'localhost',
        'PORT': 3306,
        'CHARSET': 'utf8',
        'OPTIONS': {
            'init_command': 'SET sql_mode="STRICT_TRANS_TABLES"'
        }
    }
}
```

## 初始化数据库
在命令行下进入项目所在目录，输入：
```
python manage.py makemigrations
python manage.py migrate
```
这样Django所需的自己的表会在数据库中建立。
这时可以到数据库中查看是否已经有django的自己的表。


## 建立应用
代码都是在应用中开发，在命令行下进入工程目录，然后输入：
```
python manage.py startapp <app_name>
```
之后就会在工程目录下建立一个应用目录，下面产生一些初始化的python文件。

## 建立应用的模型
模型是用来处理数据的，是程序与数据库表之间的桥梁，或者可以看到数据库表在代码中的映射，属于ORM模式。

每个模型就是一个Python中的class，并且对应一个数据库表。自定义class需要继承django.db.models.Model类。成员变量名称对应数据库表中字段名称，使用models.CharField, DateTimeField, IntegerField代表其类型。如果表名与类名不想使用相同的方式，可以定义一个内部类class Meta: 其中属性db_table的值为表名。

模型定义之后，可以使用命令更新到数据库中（在数据库中建立对应的表）：
```
python manage.py makemigrations
python manage.py migrate
```

如果是想从数据库表导出模型类，那么使用命令：
```
python manage.py inspectdb > models.py
```

## 注册应用
修改settings.py中的INSTALLED_APPS选项：
```
INSTALLED_APPS = [
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',

    'your_app_name',
]
```

## 建立view方法
view方法是在用户调用URL后，用来处理请求的方法，并返回处理结果。
view方法写在应用的views.py中，也可以放在其他python文件。只是形式为：
```
def hello(request):
    if request.method == 'GET':
        do_something()
    elif request.method == 'POST':
        do_something_else()

    return HttpResponse("Hello World!", content_type="text/html");
```


## 配置URL映射
在你的应用中建立urls.py文件，并且建立url对应的view方法。

```
from django.conf.urls import url

from . import views

urlpatterns = [
    url(r'^hello$', views.hello, name='hello'),
]
```

然后在把应用的url加入到整个工程的url映射中。修改项目目录下的urls.py文件：
```
from django.conf.urls import url, include
from django.contrib import admin

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^api/', include('yourappname.urls')),
]
```

## 运行系统
输入命令:
```
python manage.py runserver
```
如果想监听不同IP和端口可以使用
```
python manage.py runserver 192.168.0.100:8080
```


## 建立django的后台管理页面
Django自带了后台管理页面，只要使用下面命令建立一个超级管理员用户：
```
python manage.py createsuperuser
```
然后按照提示输入用户名，密码和邮箱就可以了。

最后打开浏览器输入: http://localhost:8000/admin

@完




