MySQL移动数据目录出现权限问题
===============
环境: ubuntu 14.04.4 LTS


## 现象
今天把/var/lib/mysql下的数据文件移动到其他目录下，之后发现启动mysql报错，并且mysql无法运行。具体的操作如下
```
# service mysql stop
# mv /var/lib/mysql /data
# serivce mysql start
```

报出的错误信息为：
```
170425  9:55:36 [Warning] Using unique option prefix myisam-recover instead of myisam-recover-options is deprecated and will be removed in a future release. Please use the full name instead.
170425  9:55:36 [Note] Plugin 'FEDERATED' is disabled.
/usr/sbin/mysqld: Can't find file: './mysql/plugin.frm' (errno: 13)
170425  9:55:36 [ERROR] Can't open the mysql.plugin table. Please run mysql_upgrade to create it.
170425  9:55:36 InnoDB: The InnoDB memory heap is disabled
170425  9:55:36 InnoDB: Mutexes and rw_locks use GCC atomic builtins
170425  9:55:36 InnoDB: Compressed tables use zlib 1.2.8
170425  9:55:36 InnoDB: Using Linux native AIO
170425  9:55:36 InnoDB: Initializing buffer pool, size = 128.0M
170425  9:55:36 InnoDB: Completed initialization of buffer pool
170425  9:55:36  InnoDB: Operating system error number 13 in a file operation.
InnoDB: The error means mysqld does not have the access rights to
InnoDB: the directory.
InnoDB: File name ./ibdata1
InnoDB: File operation call: 'open'.
InnoDB: Cannot continue operation.
```

## 原因
最初以为是简单的权限问题，就把/data/mysql目录赋权给mysql用户
```
# chown -R mysql:mysql /data/mysql
```
但是再次启动mysql后，还是遇到同样的错误。上网查了一下，原因是由于现今的Linux系统都采用AppArmor来限制文件和目录的执行权限。

## 解决办法

所以需要配置AppArmor让mysqld可以访问/data/mysql目录。具体操作如下：
```
# vi /etc/apparmor.d/usr.sbin.mysqld
```
在此文件末尾增加你的目录（可以按照/var/lib/mysql的目录改）
```
/usr/sbin/mysqld {
  #include <abstractions/base>
  #include <abstractions/nameservice>
  #include <abstractions/user-tmp>
  #include <abstractions/mysql>
  #include <abstractions/winbind>

  capability dac_override,
  capability sys_resource,
  capability setgid,
  capability setuid,

  network tcp,

  /etc/hosts.allow r,
  /etc/hosts.deny r,

  /etc/mysql/*.pem r,
  /etc/mysql/conf.d/ r,
  /etc/mysql/conf.d/* r,
  /etc/mysql/*.cnf r,
  /usr/lib/mysql/plugin/ r,
  /usr/lib/mysql/plugin/*.so* mr,
  /usr/sbin/mysqld mr,
  /usr/share/mysql/** r,
  /var/log/mysql.log rw,
  /var/log/mysql.err rw,
  /var/lib/mysql/ r,
  /var/lib/mysql/** rwk,
  /var/lib/mysql-files/ r,
  /var/lib/mysql-files/** rwk,
  /var/log/mysql/ r,
  /var/log/mysql/* rw,
  /var/run/mysqld/mysqld.pid rw,
  /var/run/mysqld/mysqld.sock w,
  /run/mysqld/mysqld.pid rw,
  /run/mysqld/mysqld.sock w,

  /sys/devices/system/cpu/ r,
  # Site-specific additions and overrides. See local/README for details.
  #include <local/usr.sbin.mysqld>
  /data/mysql/ r,
  /data/mysql/** rwk,
}

```
如果不写第一句的`/data/mysql/ r`，则mysql能启动，但是进入mysql后输入命令会提示下面错误：
```
ERROR 1018 (HY000): Can't read dir of '.' (errno: 13
```


@完

---------------
参考：
- <https://serverfault.com/questions/473789/mysql-doesnt-start-after-relocating-data-dir>
- <http://blog.csdn.net/wzhwho/article/details/4179154>
