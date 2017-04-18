网络安装Ubuntu16.04
===================


## 搭建PXE服务器
PXE是Pre-boot Execution Environment，预启动执行环境。是通过网络安装任何linux系统最重要的步骤。
首选搭建PXE服务器，然后再把PXE服务器和要安装Ubuntu16.04的主机放在同一个局域网中。最后设置被安装主机以pxe方式启动。
有个文章介绍原理很清楚 <http://www.cnblogs.com/zhangjianghua/p/5872269.html>

### 安装dhcp (不是dhcp3)
```
sudo apt-get install isc-dhcp-server
```
配置网卡名称`sudo vi /etc/default/isc-dhcp-server`中的INTERFACES改为自己网卡名称.(默认为空字符串)

配置dhcpd，编辑`sudo vi /etc/dhcp/dhcpd.conf`文件
```
option domain-name "localhost";  # 必须要改，否则syslog报异常，pxe client IP无法获取
option domain-name-servers 本机IP地址;  # 必须要改

allow booting;
allow bootp;

subnet 192.168.1.0 netmask 255.255.255.0 {
    range 192.168.1.10 192.168.1.30;
    option subnet-mask 255.255.255.0;
    option routers 本机IP地址;
    option broadcast-address 192.168.1.255;
    next-server 本机IP地址;
    filename "pxelinux.0";
}

# 下面不写也行，就是为了指定主机的
host anyname {
    hardware ethernet 被安装主机MAC地址;
    filename "pxelinux.0";
}

```

以上测试真实有效。注意：后面都有分号结束； 本机IP地址必须也要在192.168.1.0/24网段中。


启动dhcp服务
```
sudo /etc/init.d/isc-dhcp-server start
```
注意：  为了不影响公司的局域网，最后与外部的局域网断开，只把pxe服务器和被安装主机用交换机连接起来。或者使用与公司不同的网段。


### 安装tftp服务
```
sudo apt-get install tftpd-hpa
sudo apt-get install tftp-hpa
sudo apt-get install inetutils-inetd
```
这个命令是/usr/sbin/in.tftpd，其服务的脚本是/etc/init.d/tftpd。所以你可以通过下面启动此服务。
```
sudo service tftpd-hpa start
```
服务启动后，可以通过tftp客户端命令来尝试tftpd服务是否正常运行。怎么测试呢？首先新建一个测试文件放到/var/lib/tftpboot/目录下。然后输入以下命令：
```
sudo tftp localhost
> get <测试文件名>
> quit
```
如果文件下载到了当前目录下，那就说明测试成功。
网上好多文章都说要修改配置文件/etc/default/tftpd-hpa，修改内容如下：
```
TFTP_USERNAME="tftp"
TFTP_DIRECTORY="/var/lib/tftpboot"
TFTP_ADDRESS=":69"
TFTP_OPTIONS="--secure"
# 下面是新增的，但不知道为什么要加，估计不写也行
RUN_DAEMON="yes"
OPTIONS="-l -s /var/lib/tftpboot"
```


### 安装apache2
我不太喜欢NFS，感觉太慢，还是apache比较快些。
```
sudo apt-get install apache2
```
我是使用Oracle VirtualBox安装的ubuntu16.04系统作为pxe服务器，所以需要把网络改成**桥接网络**，不用重启虚拟机，只要在ubuntu系统中重启网络就可以了。运行`/etc/init.d/networking restart`命令获取一下最近IP地址.
这时候访问http://host_ip/地址就会显示apache默认主页。 （apache安装完了自动启动）



### 下载ISO

把ubuntu的ISO放到/var/www/html（apache的web根目录）目录下。
```
sudo wget https://mirror.tuna.tsinghua.edu.cn/ubuntu-releases/16.04/ubuntu-16.04.2-server-amd64.iso
```
文件大约829M。


ISO不能直接用，除非使用nfs。需要先mount
```
sudo mount -o loop ubuntu.iso /mnt
```
然后把里面的install/netboot中所有内容复制到/var/lib/tftpboot中。



### 下载netboot文件 (此步骤不要做，太坑了，要用ISO中install/netboot替代)
netboot文件下载后放到/var/lib/tftpboot目录下，用于引导被安装主机的启动。
```
cd /var/lib/tftpboot
wget  http://archive.ubuntu.com/ubuntu/dists/precise/main/installer-i386/current/images/netboot/netboot.tar.gz
```
文件大约20M。
下载之后开始解压
```
sudo tar -xzvf netboot.tar.gz
```
解压后就会出现pxelinux.0文件。

新版本ubuntu的pxe已经不在/var/lib/tftpboot/pxelinux.cfg/default文件中了，而是在/var/lib/tftpboot/ubuntu-installer/i386/boot-screens/txt.cfg文件中。label后面也不是Linux了，而是install。更改append后面的内容.
```
append vga=788 initrd=ubuntu-installer/i386/initrd.img  ks=http://192.168.1.10/ks.cfg   #告诉系统，从哪里获取ks.cfg文件
```
上面的配置说明使用ks.cfg来安装ubuntu，那么这个ks.cfg要放到/var/www/html目录下。



## 安装ubuntu
给被安装的主机加电，进入BIOS，选择从网卡启动（或者被称为PXE）。然后就会进入DHCP获取阶段，这时候如果出现问题，那么就可以到pxe服务器上查看/var/log/syslog日志报错信息。如果没有任何信息，看看防火墙有没有关闭。

如果是正常信息，会打印出
```
dhcpd[进程ID] DHCPDISCOVER from MAC地址 via 网卡名称
dhcpd[进程ID] DHCPOFFER on 分配的IP地址 to MAC地址 via 网卡名称
dhcpd[进程ID] DHCPREQUEST for 分配IP地址(DHCP服务IP地址) from MAC地址 via 网卡名称
dhcpd[进程ID] DHCPACK on 分配的IP地址 to MAC地址 via 网卡名称
```

之后被安装主机会显示一个图形菜单。选择install， 竟然从cn.archive.ubuntu.com上下载镜像，而不是从我的pxe服务器。


安装过程中遇到太多的坑了。
- 首先是网卡识别不了---> 发现是由于netboot与ISO中估计不一样导致，使用ISO中install/netboot覆盖/var/lib/tftpboot就可以了。
- 之后出现Installation step failed错误，查了网上<http://www.michaelm.info/blog/?p=1378>说的解决方法，但是d-i是什么鬼呀。又去查了一下，找到这个<http://mole1230.blog.51cto.com/837625/1430489>。算了没时间看了。



### 总结
- 先把ISO挂载到/mnt目录下，把里面的东西`cp -avr /mnt/* /var/www/html/ubuntu/`复制到apache下面。
- 使用ISO中的netboot: `cp -avr /mnt/install/netboot/* /var/lib/tftpboot`
- 可能需要ks.cfg，直接使用ISO是不行（东西拿出来散放），除非使用nfs方式（这个我没有试验）。
```
vi /var/lib/tftpboot/pxelinux.cfg/default

#在最后增加如下内容
label linux
        kernel ubuntu-installer/amd64/linux
        append ks=http://192.168.1.20/ks.cfg vga=normal initrd=ubuntu-installer/amd64/initrd.gz 

还有一种
label linux
        kernel ubuntu-installer/amd64/linux
        append vga=798 initrd=ubuntu-installer/amd64/initrd.gz url=http://<your_local_server>/preseed.seed

```
preseed.seed文件在/var/www/html中，内容如下：
```
d-i clock-setup/utc boolean true
d-i time/zone string Europe/Ljubljana
d-i console-setup/ask_detect boolean false
d-i console-setup/layoutcode string sl
d-i debian-installer/language string English
d-i debian-installer/country string SI
d-i debian-installer/locale string en_US.UTF-8
d-i keyboard-configuration/layout select Slovenian
d-i keyboard-configuration/variant select Slovenian
d-i keyboard-configuration/layoutcode string sl
d-i keyboard-configuration/xkb-keymap select sl
d-i mirror/country string manual
d-i mirror/http/hostname string netboot.abakus.si （替换成你的）
d-i mirror/http/directory string /iso/ubuntus1404_64（替换成你的）
d-i mirror/http/proxy string
d-i apt-setup/restricted boolean true
d-i apt-setup/universe boolean true
d-i apt-setup/backports boolean true
d-i apt-setup/services-select multiselect security
d-i apt-setup/security_host string netboot.abakus.si
d-i apt-setup/security_path string /iso/ubuntus1404_64/  （替换成你的）
d-i live-installer/net-image string http://<your_local_server>/iso/install/filesystem.squashfs
#d-i debian-installer/allow_unauthenticated boolean true
d-i preseed/late_command string wget http://<your_local_server>/boot/sources.list -O /target/etc/apt/sources.list
```

上面过程参考：<http://linux.opm.si/programska-oprema/ubuntu-14-04-network-install>。
不知道好不好使 :D


- 中间过程如果出问题，可以点击continue继续安装，如果不能继续，那就....认倒霉
- 后来才知道，dnsmasq竟然可以替换dhcp+tftp两个软件。
- 没有时间折腾的人，尽量还是不要使用pxe安装了。
- 最后还是通过公网的ubuntu安装的，本地安装没有测试通过。也就是说通过本地pxe让ubuntu引导起来，安装过程所需的文件还是从网上下载的。


------------
参考网站:
- 网络安装ubuntu <http://wiki.ubuntu.org.cn/%E7%BD%91%E7%BB%9C%E5%AE%89%E8%A3%85ubuntu>
- <http://blog.csdn.net/bluebird_shao/article/details/8206680>
- 官方介绍 <https://help.ubuntu.com/community/PXEInstallServer>
- <http://gm100861.blog.51cto.com/1930562/934661>
- <http://mole1230.blog.51cto.com/837625/1430489>
- <http://www.michaelm.info/blog/?p=1378>


参考资源：
- <http://cn.archive.ubuntu.com/>
- <http://archive.ubuntu.com>