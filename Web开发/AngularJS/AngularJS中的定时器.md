AngularJS定时器的使用
==========

有时候想每隔一段时间就运行一个函数。这在AngularJS中通过interval定时器来实现。

```
$scope.mytimer = $interval(function() {
    console.log("do something here!");
}, 5000);
```

由于AngularJS是单页面应用，所以在页面切换之后，想停止之前建立的定时器。
```
$scope.$on('destroy', function() {
    $interval.cancel($scope.mytimer);
});

```

如果在运行过程中报出Angular: $interval undefined的错误，那么是由于$interval这个服务并没有在范围内，或者没有引入。
```
var MainController = function($scope, $http) {
}

需要改成:

var MainController = function($scope, $http, $interval) {
}
```



@完

-----------
参考：
- <http://blog.csdn.net/liwusen/article/details/52138882>
- <https://stackoverflow.com/questions/29788769/angular-interval-undefined>


