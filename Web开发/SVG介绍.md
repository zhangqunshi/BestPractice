SVG介绍
=====
SVG是指可缩放矢量图（Scalable Vector Graphics)。SVG使用XML格式来定义图形。SVG可以直接嵌入到HTML页面中。

## 位图和矢量图
位图(Bitmap)是由很多具有自己颜色的像素组成的图像。放大后需要通过插值计算来补充像素，所以会失真。
矢量图的有点：
- 文件小
- 缩放，旋转或者改变图形不失真
- 线条，颜色平滑，锯齿不明显。

矢量图最终也要转成位图来表示。

## SVG形状
SVG标签
```
<svg width="200" height="200" version="1.1" xmlns="http://www.w3.org/2000/svg">
...
</svg>
```
SVG有七种图形元素。
### 矩形
```
<rect x="10" y="10" width="100" height="200" />

<rect x="10" y="10" width="100" height="200" 
style="fill: yellow; stroke: red; stroke-width:2; opacity:0.5" />
```
参数含义
- x为矩形左上角x坐标
- y为矩形左上角y坐标
- width为矩形宽度
- height为矩形高度
- rx为圆角矩形椭圆在x方向上的半径
- ry为圆角矩形椭圆在y方向上的半径
- style用来指定样式

### 圆形
```
<circle cx="100" cy="100" r="80" />
```

cx 和 cy 属性定义圆点的 x 和 y 坐标。如果省略 cx 和 cy，圆的中心会被设置为 (0, 0)

r 属性定义圆的半径。

### 椭圆形
```
<ellipse cx="100" cy="100" rx="80" ry="70"/>
```
参数：
- cx 属性定义圆点的 x 坐标
- cy 属性定义圆点的 y 坐标
- rx 属性定义水平半径
- ry 属性定义垂直半径

### 线段
```
<line x1="10" y1="20" x2="100" y2="200" />
```
参数：
- x1 属性在 x 轴定义线条的开始
- y1 属性在 y 轴定义线条的开始
- x2 属性在 x 轴定义线条的结束
- y2 属性在 y 轴定义线条的结束

### 多边形
多边形是闭合图形，起点和终点最后要连接在一起。
```
<polygon points="220,100 300,210 170,250" />
```

参数points 属性定义多边形每个角的 x 和 y 坐标


### 折线
非闭合图形
```
<polyline points="0,0 0,20 20,20 20,40 40,40 40,60" />
```

### 路径
路径使用标签`<path>`，其可以画出上面所有的图形。
```
<path d="M250 150 L150 350 L350 350 Z" />
```

下面的命令可用于路径数据：
```
M = moveto
L = lineto
H = horizontal lineto
V = vertical lineto
C = curveto
S = smooth curveto
Q = quadratic Belzier curve
T = smooth quadratic Belzier curveto
A = elliptical Arc （弧线）
Z = closepath
```
注意：以上所有命令均允许小写字母。大写表示绝对定位，小写表示相对定位（相对于当前画笔所在点）。

这里涉及到贝塞尔曲线，请看我另外一篇博客的介绍。

## 文字
```
<text x="10" y="10" dx="0" dy="10" rotate="0" textLength="100" fill="red">Hello Kris</text>
```
参数：
- x: 文字的x坐标
- y: 文字的y坐标
- dx: 往左(负数)或往右(正数)偏移
- dy: 往上(负数)或往下(正数)偏移
- rotate: 旋转角度（顺时针为正，逆时针为负）
- textLength: 文字的显示长度

## 样式
SVG中可以使用CSS的class。
```
<line class="lineStyle" ... />
```

也可以单独写
```
<line fill="yello" stroke="blue" stroke-width="2" ... />
```

也可以合并写：
```
<line style="fill:yello; stroke:blue; stroke-width:2" ... />
```

## 标记
标记（marker）是SVG中一个重要的概念。用于重复利用已经定义好的图形。
marker是一种可以连结一个或多个path、line、polyline、或polygon的顶点的标志类型。最常见的用例是绘制箭头或在输出结果的线上的标记一个（polymarker）图形。
```
<svg width="600px" height="100px">
<defs>
    <marker id="arrow" markerWidth="10" markerHeight="10" refx="0" refy="3" orient="auto" markerUnits="strokeWidth"> 
        <path d="M0,0 L0,6 L9,3 z" fill="#f00" />
    </marker> 
</defs> 

<line x1="50" y1="50" x2="250" y2="50" stroke="#000" stroke-width="5" marker-end="url(#arrow)" /> 
</svg>
```

## 滤镜
滤镜(filter)使图形更具有艺术效果。例如高斯模糊。
```
<svg xmlns="http://www.w3.org/2000/svg" version="1.1">
  <defs>
    <filter id="f1" x="0" y="0">
      <feGaussianBlur in="SourceGraphic" stdDeviation="15" />
    </filter>
  </defs>
  <rect width="90" height="90" stroke="green" stroke-width="3"
  fill="yellow" filter="url(#f1)" />
</svg>
```

## 渐变
渐变是一种从一种颜色到另一种颜色的平滑过渡。另外，可以把多个颜色的过渡应用到同一个元素上。
SVG渐变主要有两种类型：
- Linear: 线性渐变
- Radial: 放射性渐变

linearGradient元素用于定义线性渐变。
```
<svg xmlns="http://www.w3.org/2000/svg" version="1.1">
  <defs>
    <linearGradient id="grad1" x1="0%" y1="0%" x2="100%" y2="0%">
      <stop offset="0%" style="stop-color:rgb(255,255,0);stop-opacity:1" />
      <stop offset="100%" style="stop-color:rgb(255,0,0);stop-opacity:1" />
    </linearGradient>
  </defs>
  <ellipse cx="200" cy="70" rx="85" ry="55" fill="url(#grad1)" />
</svg>
```
放射性渐变
```
<svg xmlns="http://www.w3.org/2000/svg" version="1.1">
  <defs>
    <radialGradient id="grad1" cx="50%" cy="50%" r="50%" fx="50%" fy="50%">
      <stop offset="0%" style="stop-color:rgb(255,255,255);
      stop-opacity:0" />
      <stop offset="100%" style="stop-color:rgb(0,0,255);stop-opacity:1" />
    </radialGradient>
  </defs>
  <ellipse cx="200" cy="70" rx="85" ry="55" fill="url(#grad1)" />
</svg>
```

@完

------------
参考：
- <http://www.runoob.com/svg/svg-tutorial.html>
- <http://www.w3cplus.com/svg/svg-markers.html>
- <http://www.w3school.com.cn/svg/index.asp>