# Linux初始化init系统
最近把一个程序注册成为系统启动服务。发现Ubuntu16.04版本照比Ubuntu14.x版本不一样了 :-(。所以又去研究了一下Linux启动的问题。

## 历史
Linux 系统的 init 进程经历了两次重大的演进，传统的 sysvinit 已经淡出历史舞台，新的 init 系统 UpStart 和 systemd 各有特点，而越来越多的 Linux 发行版采纳了 systemd。

## sysvinit
Linux 操作系统的启动首先从 BIOS 开始，接下来进入 boot loader，由 bootloader 载入内核，进行内核初始化。内核初始化的最后一步就是启动 pid 为 1 的 init 进程。这个进程是系统的第一个进程。它负责产生其他所有用户进程。比如启动外壳 shell 后，便有了人机交互.这里，字符界面的 shell 或者 X 系统都是一种预设的运行模式。
init 以守护进程方式存在，是所有其他进程的祖先。init 进程非常独特，能够完成其他进程无法完成的任务。
Init 系统能够定义、管理和控制 init 进程的行为。它负责组织和运行许多独立的或相关的始化工作(因此被称为 init 系统)，从而让计算机系统进入某种用户预订的运行模式。

大多数 Linux 发行版的 init 系统是和 System V 相兼容的，被称为 sysvinit。这是人们最熟悉的 init 系统。

SysVinit 运行非常良好，概念简单清晰。最大弱点：启动太慢。在很少重新启动的 Server 上，这个缺点并不重要。而当 Linux 被应用到移动终端设备的时候，启动慢就成了一个大问题。为了更快地启动，人们开始改进 sysvinit，先后出现了 upstart 和 systemd 这两个主要的新一代 init 系统。

### 运行级别
Sysvinit 用术语 runlevel 来定义"预订的运行模式"。Sysvinit 检查 '/etc/inittab' 文件中是否含有 'initdefault' 项。 这告诉 init 系统是否有一个默认运行模式。如果没有默认的运行模式，那么用户将进入系统控制台，手动决定进入何种运行模式。
sysvinit 中运行模式描述了系统各种预订的运行模式。通常会有 8 种运行模式，即运行模式 0 到 6 和 S 或者 s。
每种 Linux 发行版对运行模式的定义都不太一样。但 0，1，6 却得到了大家的一致赞同：
- 0 关机
- 1 单用户模式
- 6 重启

### Sysvinit 和系统关闭
Sysvinit 不仅需要负责初始化系统，还需要负责关闭系统。在系统关闭时，为了保证数据的一致性，需要小心地按顺序进行结束和清理工作。
比如应该先停止对文件系统有读写操作的服务，然后再 umount 文件系统。否则数据就会丢失。
这种顺序的控制这也是依靠/etc/rc.d/rcX.d/目录下所有脚本的命名规则来控制的，在该目录下所有以 K 开头的脚本都将在关闭系统时调用，字母 K 之后的数字定义了它们的执行顺序。

## Upstart 简介
假如您使用的 Linux 发行版是 Ubuntu，很可能会发现在您的计算机上找不到/etc/inittab 文件了，这是因为 Ubuntu 使用了一种被称为 upstart 的新型 init 系统。

### 开发 Upstart 的缘由
Ubuntu 开发人员试图将 Linux 安装在笔记本电脑上。在这期间技术人员发现经典的 sysvinit 存在一些问题：它不适合笔记本环境。这促使程序员 Scott James Remnant 着手开发 upstart。

当 Linux 内核进入 2.6 时代时，Linux被用于桌面系统，甚至嵌入式设备。桌面系统或便携式设备的一个特点是经常重启，而且要频繁地使用硬件热插拔技术。在 2.6 内核支持下，一旦新外设连接到系统，内核便可以自动实时地发现它们，并初始化这些设备，进而使用它们。这为便携式设备用户提供了很大的灵活性。

比如打印任务，系统需要启动 CUPS 等服务，而如果打印机没有接入系统的情况下，启动这些服务就是一种浪费。Sysvinit 没有办法处理这类需求，它必须一次性把所有可能用到的服务都启动起来，即使打印机并没有连接到系统，CUPS 服务也必须启动。

针对以上种种情况，Ubuntu 开发人员在评估了当时的几个可选 init 系统之后，决定重新设计和开发一个全新的 init 系统，即 UpStart。

### Upstart 的特点
UpStart 基于事件机制，比如 U 盘插入 USB 接口后，udev 得到内核通知，发现该设备。upstart 完美地解决了即插即用设备带来的新问题。而sysvinit 运行时是同步阻塞的。UpStart具有以下优点：
- 更快地启动系统
- 当新硬件被发现时动态启动服务
- 硬件被拔除时动态停止服务

### Upstart 概念和术语
UpStart 主要的概念是 job 和 event。Job 就是一个工作单元，用来完成一件工作，比如启动一个后台服务，或者运行一个配置命令。每个 Job 都等待一个或多个事件，一旦事件发生，upstart 就触发该 job 完成相应的工作。
![UpStartToSysVinit](https://www.ibm.com/developerworks/cn/linux/1407_liuming_init3/image003.jpg)

Job
Job 就是一个工作的单元，一个任务或者一个服务。可以理解为 sysvinit 中的一个服务脚本。有三种类型的工作：

1. **task job** 代表在一定时间内会执行完毕的任务，比如删除一个文件；
2. **service job** 代表后台服务进程，比如 apache httpd。这里进程一般不会退出，一旦开始运行就成为一个后台精灵进程，由 init 进程管理，如果这类进程退出，由 init 进程重新启动，它们只能由 init 进程发送信号停止。它们的停止一般也是由于所依赖的停止事件而触发的，不过 upstart 也提供命令行工具，让管理人员手动停止某个服务；
3. **Abstract job** 仅由 upstart 内部使用，仅对理解 upstart 内部机理有所帮助。我们不用关心它。

### Job 生命周期
Upstart 为每个工作都维护一个生命周期。一般来说，工作有开始，运行和结束这几种状态。为了更精细地描述工作的变化，Upstart 还引入了一些其它的状态。比如开始就有开始之前(pre-start)，即将开始(starting)和已经开始了(started)几种不同的状态，这样可以更加精确地描述工作的当前状态。
![Job life cycle](https://www.ibm.com/developerworks/cn/linux/1407_liuming_init2/image003.jpg)

### 事件 Event
Event 可以分为三类: signal，methods 或者 hooks。
- Signal 事件是非阻塞的，异步的。发送一个信号之后控制权立即返回。
- Methods 事件是阻塞的，同步的。
- Hooks 事件是阻塞的，同步的。它介于 Signals 和 Methods 之间，调用发出 Hooks 事件的进程必须等待事件完成才可以得到控制权，但不检查事件是否成功。

### Job 和 Event 的相互协作
Upstart 就是由事件触发工作运行的一个系统，每一个程序的运行都由其依赖的事件发生而触发的。
系统初始化的过程是在工作和事件的相互协作下完成的，可以大致描述如下：系统初始化时，init 进程开始运行，init 进程自身会发出不同的事件，这些最初的事件会触发一些工作运行。每个工作运行过程中会释放不同的事件，这些事件又将触发新的工作运行。如此反复，直到整个系统正常运行起来。

究竟哪些事件会触发某个工作的运行？这是由工作配置文件定义的。
### 工作配置文件
任何一个工作都是由一个工作配置文件（Job Configuration File）定义的。这个文件是一个文本文件，包含一个或者多个小节（stanza）。每个小节是一个完整的定义模块，定义了工作的一个方面，比如 author 小节定义了工作的作者。工作配置文件存放在/etc/init 下面，是以.conf 作为文件后缀的文件。

#### expect Stanza
部分服务进程为了将自己变成后台精灵进程(daemon)，会采用两次派生(fork)的技术，另外一些服务则不会这样做。假如一个服务派生了两次，那么 UpStart 必须采用第二个派生出来的进程号作为服务的 PID。但是，UpStart 本身无法判断服务进程是否会派生两次，为此在定义该服务的工作配置文件中必须写明 expect 小节，告诉 UpStart 进程是否会派生两次。
Expect 有两种，"expect fork"表示进程只会 fork 一次；"expect daemonize"表示进程会 fork 两次。

UpStart 通过对 fork 系统调用进行计数，从而获知真正的精灵进程的 PID 号。
![forkpid](https://www.ibm.com/developerworks/cn/linux/1407_liuming_init3/image007.jpg)

#### "exec" Stanza 和"script" Stanza
一个 UpStart 工作一定需要做些什么，可能是运行一条 shell 命令，或者运行一段脚本。用"exec"关键字配置工作需要运行的命令；用"script"关键字定义需要运行的脚本。

```
# mountall.conf
description “Mount filesystems on boot”
start on startup
stop on starting rcS
...
script
  . /etc/default/rcS
  [ -f /forcefsck ] && force_fsck=”--force-fsck”
  [ “$FSCKFIX”=”yes” ] && fsck_fix=”--fsck-fix”

  ...

  exec mountall –daemon $force_fsck $fsck_fix
end script
...
```
#### "start on" Stanza 和"stop on" Stanza
"start on"定义了触发工作的所有事件。"start on"的语法很简单，如下所示：
```
start on EVENT [[KEY=]VALUE]... [and|or...]
```
EVENT 表示事件的名字，可以在 start on 中指定多个事件，表示该工作的开始需要依赖多个事件发生。多个事件之间可以用 and 或者 or 组合，"表示全部都必须发生"或者"其中之一发生即可"等不同的依赖条件。除了事件发生之外，工作的启动还可以依赖特定的条件，因此在 start on 的 EVENT 之后，可以用 KEY=VALUE 来表示额外的条件，一般是某个环境变量(KEY)和特定值(VALUE)进行比较。如果只有一个变量，或者变量的顺序已知，则 KEY 可以省略。
"stop on"和"start on"非常类似，只不过是定义工作在什么情况下需要停止。

### Upstart 系统中的运行级别
Upstart 的运作完全是基于工作和事件的。工作的状态变化和运行会引起事件，进而触发其它工作和事件。
而传统的 Linux 系统初始化是基于运行级别的，即 SysVInit。因为历史的原因，Linux 上的多数软件还是采用传统的 SysVInit 脚本启动方式，并没有为 UpStart 开发新的启动脚本，因此即便在 Debian 和 Ubuntu 系统上，还是必须模拟老的 SysVInit 的运行级别模式，以便和多数现有软件兼容。

虽然 Upstart 本身并没有运行级别的概念，但完全可以用 UpStart 的工作模拟出来。让我们完整地考察一下 UpStart 机制下的系统启动过程。
![UpStart](https://www.ibm.com/developerworks/cn/linux/1407_liuming_init2/image004.png)

在 Upstart 系统中，需要修改/etc/init/rc-sysinti.conf 中的 DEFAULT_RUNLEVEL 这个参数，以便修改默认启动运行级别。这一点和 sysvinit 的习惯有所不同，大家需要格外留意。

还有一些随 UpStart 发布的小工具，用来帮助开发 UpStart 或者诊断 UpStart 的问题。比如 init-checkconf 和 upstart-monitor
还可以使用 initctl 的 emit 命令从命令行发送一个事件。
`#initctl emit <event>`
这一般是用于 UpStart 本身的排错。


## Systemd 的简介和特点
Systemd 是 Linux 系统中最新的初始化系统（init），它主要的设计目标是克服 sysvinit 固有的缺点，提高系统的启动速度。systemd 和 ubuntu 的 upstart 是竞争对手，预计会取代 UpStart，实际上在作者写作本文时，已经有消息称 Ubuntu 也将采用 systemd 作为其标准的系统初始化系统（已经使用了在16.04版本）。

Systemd 的很多概念来源于苹果 Mac OS 操作系统上的 launchd，不过 launchd 专用于苹果系统，因此长期未能获得应有的广泛关注。Systemd 借鉴了很多 launchd 的思想，它的重要特性如下：
- 兼容SysVinit 和 LSB init scripts
- 更快的启动速度 （比 UpStart 更激进的并行启动能力, 所有的任务都同时并发执行，不管是否有依赖）
- systemd 提供按需启动能力
- Systemd 采用 Linux 的 Cgroup 特性跟踪和管理进程的生命周期. CGroup 提供了类似文件系统的接口，使用方便。当进程创建子进程时，子进程会继承父进程的 CGroup。因此无论服务如何启动新的子进程，所有的这些相关进程都会属于同一个 CGroup，systemd 只需要简单地遍历指定的 CGroup 即可正确地找到所有的相关进程，将它们一一停止即可。
- 启动挂载点和自动挂载的管理
- 实现事务性依赖关系管理
- 能够对系统进行快照和恢复 (快照功能目前在 systemd 中并不完善)
- 自带日志服务journald

### 单元的概念
系统初始化需要做的事情非常多。需要启动后台服务，比如启动 SSHD 服务；需要做配置工作，比如挂载文件系统。这个过程中的每一步都被 systemd 抽象为一个配置单元，即 unit。可以认为一个服务是一个配置单元；一个挂载点是一个配置单元；一个交换分区的配置是一个配置单元；等等。systemd 将配置单元归纳为以下一些不同的类型。然而，systemd 正在快速发展，新功能不断增加。所以配置单元类型可能在不久的将来继续增加。

每个配置单元都有一个对应的配置文件，系统管理员的任务就是编写和维护这些不同的配置文件，比如一个 MySQL 服务对应一个 mysql.service 文件。这种配置文件的语法非常简单，用户不需要再编写和维护复杂的系统 5 脚本了。

### 依赖关系
Systemd 用配置单元定义文件中的关键字来描述配置单元之间的依赖关系。比如：unit A 依赖 unit B，可以在 unit B 的定义中用"require A"来表示。这样 systemd 就会保证先启动 A 再启动 B。

### Systemd 事务
Systemd 的事务概念和数据库中的有所不同，主要是为了保证多个依赖的配置单元之间没有环形引用。

存在循环依赖，那么 systemd 将无法启动任意一个服务。此时 systemd 将会尝试解决这个问题，因为配置单元之间的依赖关系有两种：required 是强依赖；want 则是弱依赖，systemd 将去掉 wants 关键字指定的依赖看看是否能打破循环。如果无法修复，systemd 会报错。

### Target 和运行级别
systemd 用目标（target）替代了运行级别的概念，提供了更大的灵活性，如您可以继承一个已有的目标，并添加其它服务，来创建自己的目标。

### Systemd 的并发启动原理
#### 并发启动原理之一：解决 socket 依赖
绝大多数的服务依赖是套接字依赖。比如服务 A 通过一个套接字端口 S1 提供自己的服务，其他的服务如果需要服务 A，则需要连接 S1。因此如果服务 A 尚未启动，S1 就不存在，其他的服务就会得到启动错误。所以传统地，人们需要先启动服务 A，等待它进入就绪状态，再启动其他需要它的服务。Systemd 认为，只要我们预先把 S1 建立好，那么其他所有的服务就可以同时启动而无需等待服务 A 来创建 S1 了。如果服务 A 尚未启动，那么其他进程向 S1 发送的服务请求实际上会被 Linux 操作系统缓存，其他进程会在这个请求的地方等待。一旦服务 A 启动就绪，就可以立即处理缓存的请求，一切都开始正常运行。

#### 并发启动原理之二：解决 D-Bus 依赖
D-Bus 是 desktop-bus 的简称，是一个低延迟、低开销、高可用性的进程间通信机制。它越来越多地用于应用程序之间通信，也用于应用程序和操作系统内核之间的通信。很多现代的服务进程都使用D-Bus 取代套接字作为进程间通信机制，对外提供服务。比如简化 Linux 网络配置的 NetworkManager 服务就使用 D-Bus 和其他的应用程序或者服务进行交互：邮件客户端软件 evolution 可以通过 D-Bus 从 NetworkManager 服务获取网络状态的改变，以便做出相应的处理。

#### 并发启动原理之三：解决文件系统依赖
系统启动过程中，文件系统相关的活动是最耗时的，比如挂载文件系统，对文件系统进行磁盘检查（fsck），磁盘配额检查等都是非常耗时的操作。在等待这些工作完成的同时，系统处于空闲状态。那些想使用文件系统的服务似乎必须等待文件系统初始化完成才可以启动。但是 systemd 发现这种依赖也是可以避免的。

Systemd 集成了 autofs 的实现，对于系统中的挂载点，比如/home，当系统启动的时候，systemd 为其创建一个临时的自动挂载点。在这个时刻/home 真正的挂载设备尚未启动好，真正的挂载操作还没有执行，文件系统检测也还没有完成。可是那些依赖该目录的进程已经可以并发启动，他们的 open()操作被内建在 systemd 中的 autofs 捕获，将该 open()调用挂起（可中断睡眠状态）。然后等待真正的挂载操作完成，文件系统检测也完成后，systemd 将该自动挂载点替换为真正的挂载点，并让 open()调用返回。由此，实现了那些依赖于文件系统的服务和文件系统本身同时并发启动。

### Systemd 的使用

开发人员需要了解 systemd 的更多细节。比如您打算开发一个新的系统服务，就必须了解如何让这个服务能够被 systemd 管理。这需要您注意以下这些要点：
- 后台服务进程代码不需要执行两次派生来实现后台精灵进程，只需要实现服务本身的主循环即可。
- 不要调用 setsid()，交给 systemd 处理
- 不再需要维护 pid 文件。
- Systemd 提供了日志功能，服务进程只需要输出到 stderr 即可，无需使用 syslog。
- 处理信号 SIGTERM，这个信号的唯一正确作用就是停止当前服务，不要做其他的事情。
- SIGHUP 信号的作用是重启服务。
- 需要套接字的服务，不要自己创建套接字，让 systemd 传入套接字。
- 使用 sd_notify()函数通知 systemd 服务自己的状态改变。一般地，当服务初始化结束，进入服务就绪状态时，可以调用它。

此外在/etc/systemd/system 目录下还可以看到诸如*.wants 的目录，放在该目录下的配置单元文件等同于在[Unit]小节中的 wants 关键字，即本单元启动时，还需要启动这些单元。比如您可以简单地把您自己写的 foo.service 文件放入 multi-user.target.wants 目录下，这样每次都会被默认启动了。

systemd 的主要命令行工具是 systemctl。


@完

参考：
- <https://www.ibm.com/developerworks/cn/linux/1407_liuming_init1/index.html>
- <https://www.ibm.com/developerworks/cn/linux/1407_liuming_init2/index.html>
- <https://www.ibm.com/developerworks/cn/linux/1407_liuming_init3/index.html>
