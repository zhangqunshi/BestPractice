## BOM的介绍
在github上写md文件的时候，发现生成自己blog时，报出一个错误是让使用UTF-8编码，然后在Notepad++上把文件转成UTF-8时，发现菜单中有"UTF-8无BOM编码格式"。

上网查了一下BOM的定义：**byte order mark**
这个是为UTF-16和UTF-32准备的，用于标记字节序(byte order)。

「UTF-8」和「带 BOM 的 UTF-8」的区别就是有没有 BOM。即文件开头有没有 U+FEFF。


## BOM的爱恨情仇
知乎上有个比较好的文章，讲了BOM问题，下面直接引用了原文：


作者：陈甫鸼
链接：https://www.zhihu.com/question/20167122/answer/14199022
来源：知乎
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

首先，BOM是啥。这个就不解释了，Wikipedia上很详细。<http://en.wikipedia.org/wiki/Byte_order_mark>。在网页上使用BOM是个错误。BOM设计出来不是用来支持HTML和XML的。要识别文本编码，HTML有charset属性，XML有encoding属性，没必要拉BOM撑场面。虽然理论上BOM可以用来识别UTF-16编码的HTML页面，但实际工程上很少有人这么干。毕竟UTF-16这种编码连ASCII都双字节，实在不适用于做网页。其实说BOM是个坏习惯也不尽然。BOM也是Unicode标准的一部分，有它特定的适用范围。通常BOM是用来标示Unicode纯文本字节流的，用来提供一种方便的方法让文本处理程序识别读入的.txt文件是哪个Unicode编码（UTF-8，UTF-16BE，UTF-16LE）。Windows相对对BOM处理比较好，是因为Windows把Unicode识别代码集成进了API里，主要是CreateFile()。打开文本文件时它会自动识别并剔除BOM。Windows用这个有历史原因，因为它最初脱胎于多代码页的环境。而引入Unicode时Windows的设计者又希望能在用户不注意的情况下同时兼容Unicode和非Unicode（Multiple byte）文本文件，就只能借助这种小trick了。相比之下，Linux这样的系统在多locale的环境中浸染的时间比较短，再加上社区本身也有足够的动力轻装前进（吐槽：微软对兼容性的要求确实是到了非常偏执的地步，任何一点破坏兼容性的做法都不允许，以至于很多时候是自己绑住自己的双手），所以干脆一步到位进入UTF-8。当然中间其实有一段过渡期，比如从最初全UTF-8的GTK+2.0发布到基本上所有GTK开发者都弃用多locale的GTK+1.2，我印象中至少经历了三到四年。BOM不受欢迎主要是在UNIX环境下，因为很多UNIX程序不鸟BOM。主要问题出在UNIX那个所有脚本语言通行的首行#!标示，这东西依赖于shell解析，而很多shell出于兼容的考虑不检测BOM，所以加进BOM时shell会把它解释为某个普通字符输入导致破坏#!标示，这就麻烦了。其实很多现代脚本语言，比如Python，其解释器本身都是能处理BOM的，但是shell卡在这里，没办法，只能躺着也中枪。说起来这也不能怪shell，因为BOM本身违反了一个UNIX设计的常见原则，就是文档中存在的数据必须可见。BOM不能作为可见字符被文本编辑器编辑，就这一条很多UNIX开发者就不满意。顺便说一句，即使脚本语言能处理BOM，随处使用BOM也不是推荐的办法。各个脚本语言对Unicode的处理都有自己的一套，Python的 # -*- coding: utf-8 -*-，Perl的use utf8，都比BOM简单而且可靠。另一个好消息是，即使是必须在Windows和UNIX之间切换的朋友也不会悲催。幸亏在UNIX环境下我们还有VIM这种神器，即使遇到BOM挡道，我们也可以通过 set nobomb; set fileencoding=utf8; w 三条命令解决问题。最后回头想想，似乎也真就只有Windows坚持用BOM了。P.S.：本问题是自己的第150个回答。突然发现自己回答得很少很少⋯⋯P.S. 2：突然想起需要解释一下为什么说VIM去除bomb的操作需要在UNIX下完成。因为VIM在Windows环境下有一个奇怪的bug，总是把UTF-16文件识别成二进制文件，而UNIX（Linux或者Mac都可以）下VIM则无问题。这个问题从VIM 6.8一直跟着我到VIM 7.3。目前尚不清楚这是VIM的bug还是我自己那个.vimrc文件的bug。如有高手解答不胜感激。

@引用结束

## 总结
以下是一些经典语录：

- HTML有charset属性，XML有encoding属性，没必要拉BOM撑场面
- UTF-16这种编码连ASCII都双字节，实在不适用于做网页
- 通常BOM是用来标示Unicode纯文本字节流的，让文本处理程序识别txt文件是哪个Unicode编码（UTF-8，UTF-16BE，UTF-16LE）
- Windows相对对BOM处理比较好, 打开文本文件时它会自动识别并剔除BOM.
- Windows的设计者希望能在用户不注意的情况下同时兼容Unicode和非Unicode（Multiple byte）文本文件.
- Linux一步到位进入UTF-8, 过渡期至少经历了三到四年.
- BOM不受欢迎主要是在UNIX环境下，因为很多UNIX程序不鸟BOM。因为BOM本身违反了一个UNIX设计的常见原则，就是文档中存在的数据必须可见。
- UNIX环境下我们还有VIM这种神器，即使遇到BOM挡道，我们也可以通过 set nobomb; set fileencoding=utf8; w 三条命令解决问题。
- 似乎也真就只有Windows坚持用BOM了。
- UTF-8不需要BOM, 所以不含BOM的UTF-8才是标准形式.
- 微软在 UTF-8 中使用 BOM 是因为这样可以把 UTF-8 和 ASCII 等编码明确区分开
- UTF-8 的网页代码不应使用 BOM，否则常常会出错
- 写C++代码建议程序要在windows 和 mac 还有linux 上运行的话，源代码最好保存成utf-8 带bom的格式，这样比较通用一些。而用utf-16 无论大端还是小端，g++ 都不认的。或者用utf-8 不带bom格式，然后代码不要出现非ascii 127以后的字符。
- 带用bom的utf-8也是符合国际标准的
- 微软在坚持使用bom上没有错，因为这是在为用户考虑的。也许给我们这些写程序的带来了不便，但是，计算机最广泛的用户不是程序员。
- 带头的鹅和去头的鹅，有些编辑器比较傻会把去头的鹅认成鸭子
- 以UTF-8格式编码, 从notepad++  --> Mongodb里面复制东西的时候，莫名其妙多了不少的字节数。如果不安装notepad++，使用默认的记事本，那就更是个坑，默认有bom，你还无法选择。
- 就是因为这个bom，CSV导入mongodb时，第一个字段总是不正常，直接导致用第一个字段作为条件find时，出不了结果!
- utf8对ascii的兼容确实是它的好，但是这个优点在某些时候恰恰成了隐藏问题的缺点。因此bom大法好，加bom保平安.
- 为什么windows的记事本要强行给utf8加bom的原因——为了兼容旧系统的编码问题，unix阵营放弃带bom的utf8——为了让它们的上古程序能继续运行下去，这个各自有自己利益诉求的差异决定其实并不对错
- BOM（Byte-Order Mark，字节序标记）是Unicode码点U+FEFF。它被定义来放在一个UTF-16文件的开头，如果字节序列是FEFF那么这个文件就是大端序，如果字节序列是FFFE那么这个文件就是小端序。
- 用一些比较先进的跨平台编辑器比如WebStorm、SublimeText它们都是没BOM的。

----------
参考：
- https://www.zhihu.com/question/20167122
- http://jimliu.net/2015/03/07/something-about-encoding-extra/

