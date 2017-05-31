AngularJS错误 $digest already in progress - Directive in AngularJS
===========


这个问题是由于在一个$digest中启动了另外一个$digest ，因此抛出异常。写代码的时候没有规划好，导致代码嵌套的层级太多，而$apply被外层已经调用了。

解决办法很简单，就是去掉里面那层的$scope.$apply()，直接调用具体的函数。例如:

```
$http.get('url').success(function(data) {
    scope.$apply(function(s) {
        //do something
    });

});

改成

$http.get('url').success(function(data) {
    //do something

});
```

或者判断是否$$phase是否没有被设置。


```
$http.get('url').success(function(data) {
    if(!scope.$$phase)
        scope.$apply(function(s) {
            //do something
        });
});
```

@完

---------
参考：
- <https://stackoverflow.com/questions/18316877/digest-already-in-progress-directive-in-angularjs>
