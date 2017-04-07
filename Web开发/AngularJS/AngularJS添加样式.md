AngularJS添加样式
==========

通过controller.js中的某个$scope变量的值来决定div标签（或者其他html标签）的样式表。

## 示例
使用ng-style指令在div标签上
```
<body ng-app="myApp" ng-controller="myCtrl">

<div ng-style="myStyle">菜鸟教程</div>

<script>
var app = angular.module("myApp", []);
app.controller("myCtrl", function($scope) {
    $scope.myStyle = {
        "color" : "white",
        "background-color" : "coral",
        "font-size" : "60px",
        "padding" : "50px"
    }
});
</script>
</body>
```

@完

参考：<http://www.runoob.com/angularjs/ng-ng-style.html>

