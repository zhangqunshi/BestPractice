JavaScript基础语法介绍
=============

# 语法
1. 区分大小写
2. 注释：单行//, 多行 /\* ... \*/
3. 分号作为代码的结束符号。
4. 花括号为块代码。

# 变量
- 使用var来声明一个新变量。例如`var a;`, 变量默认值为undefined
- 多个变量使用逗号分隔。
- 变量类型是弱类型，就是根据赋值来决定变量类型。

# 数据类型
JavaScript具有5中基础类型：undefined, null, boolean, number, string。还有一个复杂类型: object。
查看变量属于那种类型可以通过typeof：
```
var a = 100;
console.log(typeof a);
```
## undefined和null
undefined表示一个未定义的基础类型的变量；而null表示一个空对象。所以object类型变量最好初始化为null。
```
var obj = null;
obj = {name: "kris", age: 20};
```
## boolean类型
有五种值会转为false：0, NaN, undefined, null, ""（空字符串），其他值都转成true。

## number类型
数值使用十进制。八进制前面以零开头。十六进制以0x开头。
```
var a = 100;
var b = 020;
var c = 0xFF;
```
浮点数可以使用小数点和指数。
```
var f = 3.14;
var m = 3e5;  // 表示 3 * 10^5
```
## string类型
字符串可以使用单引号或者双引号。长度使用length获取。字符拼接使用加号。

## object类型
object是拥有属性和方法的数据类型。
```
var person = new Object();
person.name = "kris";
person.age = 20;
person.grow = function() {
  this.age += 1;
}
```

# 操作符
## 算术操作符
包括：+, -, * , /, ++, --;

## 布尔操作符
非!, 与&&, 或||

## 关系操作符
大于>, 小于<, 大于等于>=, 小于等于<=, 相等==, 全等===, 不相等!=, 不全等!==

## 条件操作符
三元  5 > 3 ? true : false;

# 控制语句
if-else if-else
while, do-while
for, for-in
其中for-in用于遍历对象属性和方法：
```
for(var prop in person) {
  console.log(prop);
}
```
switch-case-default语句。
break, continue, label语句。label标记在循环之前，用于退出到最外层的label名称，在break之后指定。

# 函数
```
function add(n1, n2) {
  return n1 + n2;
}
或者

var add = function(n1, n2) {
  return n1 + n2;
}
```

# 对象
创建对象：
```
var person = {
  name: "kris",
  age: 20,
  grow: function() {
    this.age += 1;
  }
}
或者
function Person(name, age) {
  this.name = name;
  this.age = age;
  this.grow = function() {
    this.age += 1;
  }
}
var p = new Person("kris", 20);
```

# 数组
JavaScript的数组其实是一种对象。
```
var a = [1,2,3];
或者
var a = new Array(1, 2, 3);
var b = new Array(20); //长度为20，没有赋值的数组
```

# 访问和修改HTML元素
```
document.getElementById("id");
document.getElementByTagName("p");
document.getElementByClassName("className");
```
使用style属性修改元素的样式表，例如：
```
var div = document.getElementById('divid');
div[0].style.backgroundColor = "red";
```
注意：在CSS中的background-color，由于变量名不能带有横线，所以改成backgroundColor。

# 添加和删除节点
```
var p = document.createElement("p");
p.innerHTML = "hello";
var div = document.getElementById("divid");
div.appendChild(p);
```
删除节点为： `div.removeChild(x);`。

# 事件
```
var p = document.getElementById("pid");
p.onclick = function() {
  this.innerHTML = "Hello, kris";
}
```
事件种类包括：
- onload: 页面或者图片加载完成
- onclick： 鼠标点击。
- ondbclick：鼠标双击
- onkeydown： 键盘按键按下
- onkeypress：按下并松开
- onkeyup： 按键松开
- onmousedown： 鼠标按下
- onmousemove： 鼠标移动
- onmouseout：鼠标移开
- onmouseover：鼠标在上面
- onmouseup： 鼠标松开。

@待续
