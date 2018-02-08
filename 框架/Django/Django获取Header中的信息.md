Django获取Header中的信息
====

今天需要从header中获取一些信息，查了一些资料，需要注意一下几点：
1. request.META.get("header key") 用于获取header的信息
1. 注意的是header key必须增加前缀HTTP，同时大写，例如你的key为username，那么应该写成：request.META.get("HTTP_USERNAME")
1. 另外就是当你的header key中带有中横线，那么自动会被转成下划线，例如my-user的写成： request.META.get("HTTP_MY_USER")
