JavaScript数组forEach循环
=============

今天写JavaScript代码把forEach循环数组忘记写法了，在此记录一下以防止未来忘记。
```
let a = [1, 2, 3];
a.forEach(function(element) {
    console.log(element);
});
```
有趣的是，forEach是a的一个函数。

## 语法
```
array.forEach(callback(currentValue, index, array){
    //do something
}, this)

array.forEach(callback[, thisArg])
```
- callback: 为数组中每个元素执行的函数，该函数接收三个参数：
    - currentValue(当前值)数组中正在处理的当前元素。
    - index(索引) 数组中正在处理的当前元素的索引。
    - array forEach()方法正在操作的数组。
- thisArg: 可选参数。当执行回调函数时用作this的值(参考对象)。

## 描述
forEach 方法按升序为数组中含有效值的每一项执行一次callback 函数，那些已删除（使用delete方法等情况）或者未初始化的项将被跳过（但不包括那些值为 undefined 的项）（例如在稀疏数组上）。

如果给forEach传递了thisArg参数，当调用时，它将被传给callback 函数，作为它的this值。否则，将会传入 undefined 作为它的this值。callback函数最终可观察到this值，这取决于 函数观察到this的常用规则。

forEach 遍历的范围在第一次调用 callback 前就会确定。调用forEach 后添加到数组中的项不会被 callback 访问到。如果已经存在的值被改变，则传递给 callback 的值是 forEach 遍历到他们那一刻的值。已删除的项不会被遍历到。如果已访问的元素在迭代时被删除了(例如使用 shift()) ，之后的元素将被跳过 - 参见下面的示例。

forEach() 为每个数组元素执行callback函数；不像map() 或者reduce() ，它总是返回 undefined值，并且不可链式调用。典型用例是在一个链的最后执行副作用。

**注意**： **没有办法中止或者跳出 forEach 循环，除了抛出一个异常**。如果你需要这样，使用forEach()方法是错误的，你可以用一个简单的循环作为替代。如果您正在测试一个数组里的元素是否符合某条件，且需要返回一个布尔值，那么可使用 Array.every 或 Array.some。如果可用，新方法 find() 或者findIndex() 也可被用于真值测试的提早终止。

## 示例
```
function logArrayElements(element, index, array) {
    console.log("a[" + index + "] = " + element);
}

// 注意索引2被跳过了，因为在数组的这个位置没有项
[2, 5, ,9].forEach(logArrayElements);

// a[0] = 2
// a[1] = 5
// a[3] = 9

[2, 5, "", 9].forEach(logArrayElements);
// a[0] = 2
// a[1] = 5
// a[2] = 
// a[3] = 9

[2, 5, undefined ,9].forEach(logArrayElements);
// a[0] = 2
// a[1] = 5
// a[2] = undefined
// a[3] = 9


let xxx;
// undefined

[2, 5, xxx ,9].forEach(logArrayElements);
// a[0] = 2
// a[1] = 5
// a[2] = undefined
// a[3] = 9
```


------------
参考:
- http://www.webhek.com/post/javascript-loop-foreach-for-in-for-of.html
- https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Global_Objects/Array/forEach
