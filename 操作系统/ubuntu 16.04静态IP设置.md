Ubuntu16.04 静态IP配置
============

## 修改配置

登录系统后，编辑文件/etc/network/interfaces。原始的内容如下：

```
# This file describes the network interfaces available on your system
# and how to activate them. For more information, see interfaces(5).

source /etc/network/interfaces.d/*

# The loopback network interface
auto lo
iface lo inet loopback

# The primary network interface
auto eno1
iface eno1 inet dhcp
# This is an autoconfigured IPv6 interface
iface eno1 inet6 auto
```

修改成如下内容：

```
# This file describes the network interfaces available on your system
# and how to activate them. For more information, see interfaces(5).

source /etc/network/interfaces.d/*

# The loopback network interface
auto lo
iface lo inet loopback

# The primary network interface
auto eno1

iface eno1 inet static
address 192.168.0.100
netmask 255.255.255.0
gateway 192.168.0.254

# This is an autoconfigured IPv6 interface
iface eno1 inet6 auto
```

## 重启网络

```
sudo /etc/init.d/networking restart
```
