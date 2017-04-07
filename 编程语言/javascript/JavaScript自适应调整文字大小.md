JavaScript自适应调整文字大小
============
今天有个任务，发现页面上的数字由于太长而与其他数字重叠了。这个数字还不能像文字那样只显示一部分，必须全部显示。想了一些办法都不行，最后把超过1000变成1K，大于K改成M，这样办法都行，还是长呀。

注意：我这个任务不是在不同屏幕下文字大小调整，而是同一种屏幕下调整字体大小。

在网上还找到了一种方法<https://www.zhuwenlong.com/blog/article/528611f363c705fc73000001>，但是没有使用。

同时又去试了一下svg是否可以自动调整文字大小，发现不行(可能是我没搞清楚，如果把SVG当成一个图片可能好使)。

最后想了一个办法，根据数字的长度动态修改文字的大小。系统使用AngularJS写的。所以在controller.js中设定了一个$scope.myStyle的样式，并且把样式放在div上：
```
<div ng-style="myStyle">...</div>
```
然后在controller.js中判断字符串长度。首先先把数字变成字符串并且都连接在一起，然后判断最终的字符串长度。

示例:
```
if(text_length <= 6) {
    $scope.myStyle = {
       "font-size": "34px",
    }
} else if(text_length <= 8) {
    $scope.myStyle = {
       "font-size": "32px",
    }
} else if(text_length <= 10) {
    $scope.myStyle = {
       "font-size": "30px",
    }
} else if(text_length <= 12) {
    $scope.myStyle = {
       "font-size": "26px",
    }
} else if(text_length < 18) {
    $scope.myStyle = {
       "font-size": "22px",
    }
} else {
    $scope.myStyle = {
       "font-size": "20px",
    }
}
```

最后的显示效果还可以，不过调试的过程要自己慢慢调。:D


@完



