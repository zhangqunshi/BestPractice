Freemarker不显示对象的属性值的原因
=====

今天使用Freemarker在springboot项目中通过模板生成一些html文件。但是发现没有显示对象的属性。
找了很长时间，终于发现不显示对象的属性可能是两个原因造成的：

- 属性没有getter方法。这个比较奇葩，估计是freemarker显示属性调用的是getter方法，虽然写的是属性名称。
- 对象是内部类的对象。这个真的让我花了几个小时，没有任何错误提示，坑人呀。最后变成普通的类就可以了。

以上问题希望能帮助到后面踩坑的人。

:D

