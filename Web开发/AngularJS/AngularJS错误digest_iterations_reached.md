Error: 10 $digest() iterations reached. Aborting! with dynamic sortby predicate

===========

这个错误是由于在ng-repeat或者其他循环中改变了被循环的对象内容，引发了新一轮的渲染，而导致的再次循环。

例如：
```
<div ng-init="user.score=user.id+1"> 
```

所以AngularJS推荐不要再view中改变模型，不推荐view中使用ng-init。而是在Controller或者Directive中修改model。



@完

---------
参考：
- <https://stackoverflow.com/questions/14376879/error-10-digest-iterations-reached-aborting-with-dynamic-sortby-predicate>



