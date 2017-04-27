LoadRunner使用
======

软件版本：12.53 build 1203
操作系统: Windows7

以下内容摘录自LoadRunner的官方帮助文档。

## 介绍
LoadRunner现在是HP公司的产品，其用于进行对网站的压力测试。现在这款产品免费下载，提供最大50个用户的测试，要想更多就需要付费了。
此产品一共有3个组件（软件）

- VuGen(Virtual User generator) 目的是生成测试脚本，可以采用录制的方式生成测试脚本(被称作Vuser script)
- Controller 用于组织、驱动、管理和监视压力测试
- Analysis 用于分析和图形化显示测试结果和报告，比较压力测试结果.
- Load Generator, 计算机其运行Vuser来生成一个压力给被测试的系统。(估计指的是计算机本身，不是软件)

安装LoadRunner之后就会在**开始**菜单中看到HP Software->HP LoadRunner下有上面三个程序。

## 术语

- Scenario 定义在测试会话中发送的时间。（我的理解就是测试场景，就像开发中的代码工程一样，组织测试所需的部件和动作）
- Virtual User或Vusers 模拟测试人员的动作，一个测试场景中可以包含成千上万的Vuser。
- Vuser Script 记录一个测试过程中的所有动作。
- Protocol 测试中与被测试的服务器连接采用的协议，一般web就是HTTP
- Transaction 把测试过程划分为事务，可以测量事务执行的时间
- Script footprint 执行测试脚本过程中不同资源的使用率，例如内存，CPU和磁盘。

## 测试过程

典型的测试过程为：

1. 计划 （非软件操作，而是测试目标和测试需求，你总要知道自己测啥吧，达到什么并发量吧，这才能决定代码合不合格）
2. 建立Vuser Script （使用VuGen软件捕获用户动作来生成测试脚本）
3. 定义Scenario  （使用Controller软件来建立压力测试环境）
4. 运行Scenario （使用Controller来驱动，管理和监视测试）
5. 分析结果  （使用Analysis软件来建立图形化的报告，和评估系统性能）

## 采用HP自带的例子进行测试
这个软件本身提供了一个示例应用。也就是说LoadRunner里面自带了个写好的旅游系统，你需要把它运行起来，再用LoadRunner软件去测试它，明白了么？
这个例子系统是个Web系统，其具有搜索航班、订机票、检查行程等功能。这个演示系统叫做HP Web Tours，要把它运行起来需要以下条件：

- LoadRunner需要安装到默认目录（改了安装目录的人别想了，直接重新安装吧，要不就使用自己的系统测试）
- 必须是IE10+以上的版本
- Java 7u65 or Java8
- 打开控制面板--> Java --> 安装选项卡 --> 在例外网站中添加 http://127.0.0.1:1080 和 http://localhost:1080

启动例子程序： 开始菜单--> 所有程序 --> HP Software --> HP LoadRunner -> Samples -> Web --> Start HP Web Tours Server.
然后打开浏览器，访问: <http://127.0.0.1:1080/WebTours/index.htm>，应该会看到登录页面。输入用户名：jojo, 密码: bean。
登录进入后，选择Flights-> 选择Arrival city为Los Angeles，然后一直选择continue，直到最后订票完成。

## 1. 建立Vuser Script
Vuser脚本就是采用录制和回放来生成测试脚本。

### 建立空白Vuser Script
打开Virtual User generator软件，选择工具栏左上角的`New Solution`，然后在弹出的对话框中Single Protocol中选择`Web-HTTP/HTML`，然后点击`create`按钮。

### 录制Vuser Script
上面建立的是空白的脚本，这步骤是要往空白脚本里面添加内容。采用录制的方式。

点击菜单中的Record --> Develop Script 或者点击工具栏上的 Develop Script。都是一样的。然后会自动弹出浏览器窗口。
由于我没有按照IE10及以上版本，所以LoadRunner给我指定的是Firefox浏览器（工具栏上能看到）。

在浏览器上输入http://127.0.0.1:1080/WebTours/index.htm（奇怪的是左侧是录制过程界面，右面会显示你系统的网页），打开后登陆，选择预订机票的所有动作，都会在左侧窗口显示出来。
整个显示效果还不错。而且上面还有工具条控制录制过程，停止后还能点击Replay回放整个过程。可视化过程做的很不错。
我记得老版本是悬浮一个工具条，但新版本改进了，左侧出现录制过程。

点击"Stop Recording"按钮后，关闭浏览器，回到LoadRunner后就会看到Action对应的脚本文本中显示了测试过程对应的代码。

### 如何替换变化的值
对于网站中个变化的属性，例如Session ID，如果每次测试都使用相同的值是测试不了的。所以需要让LoadRunner临时取得这个值，那么就要用到correlate功能。
步骤：
- 打开Design Studio： 在菜单Design --> Design Studio
- VuGen软件会自动发现可能的动态值，并提示给你
- 选择其中的Session ID，点击Correlate按钮，VuGen改变Session ID的状态为Applied。
- 关闭Design Studio

### 设定脚本的运行时表现
测试脚本运行过程中应该干嘛的配置，例如Action重复几次，模拟用户的Think time的时间间隔。
Pacing是用来控制每次迭代测试过程之间的时间间隔。
注意：在Controller软件中也有运行时的配置。

## 2. 运行Vuser Script，执行压力测试
启动Controller软件，然后设定运行的Vuser个数，运行时的其他信息，然后点击start按钮来执行压力测试。

## 3. 分析结果
打开Analysis软件，导入Senario，然后就可以看到report了。这时可以建立SLA，与压力测试的结果进行比较，对比出差距。


LoadRunner有官方的使用手册，那个手册在安装LoadRunner时自带的，讲的非常详细。


-----------
参考
- <http://blog.csdn.net/anialy/article/details/32086815>
- <http://blog.csdn.net/liusong0605/article/details/30287155/>