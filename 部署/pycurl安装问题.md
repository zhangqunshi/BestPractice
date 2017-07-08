pycurl安装问题
=======

之前人写的代码中依赖pycurl，所以准备在ubuntu14.04.4 LTS系统上安装一下。发现了不少问题。

## Could not run curl-config

最开始遇到问题就是下面这个错误
```
root@ubuntu:~# pip install pycurl
Collecting pycurl
  Downloading http://mirrors.aliyun.com/pypi/packages/12/3f/557356b60d8e59a1cce62ffc07ecc03e4f8a202c86adae34d895826281fb/pycurl-7.43.0.tar.gz (182kB)
    100% |████████████████████████████████| 184kB 854kB/s 
    Complete output from command python setup.py egg_info:
    Traceback (most recent call last):
      File "<string>", line 1, in <module>
      File "/tmp/pip-build-0IvaXo/pycurl/setup.py", line 823, in <module>
        ext = get_extension(sys.argv, split_extension_source=split_extension_source)
      File "/tmp/pip-build-0IvaXo/pycurl/setup.py", line 497, in get_extension
        ext_config = ExtensionConfiguration(argv)
      File "/tmp/pip-build-0IvaXo/pycurl/setup.py", line 71, in __init__
        self.configure()
      File "/tmp/pip-build-0IvaXo/pycurl/setup.py", line 107, in configure_unix
        raise ConfigurationError(msg)
    __main__.ConfigurationError: Could not run curl-config: [Errno 2] No such file or directory
```
这个错误是由于没有安装curl导致的，所以解决办法也很简单。
```
root@ubuntu:~# apt install curl
```
之后运行`pip install pycurl`还是报这个错误！！！:-(

后来查了一下需要安装curl的dev包。
```
 apt-get install libcurl4-openssl-dev
```

最后再安装pycurl，终于成功：
```
root@ubuntu:~# pip install pycurl
Collecting pycurl
  Downloading http://mirrors.aliyun.com/pypi/packages/12/3f/557356b60d8e59a1cce62ffc07ecc03e4f8a202c86adae34d895826281fb/pycurl-7.43.0.tar.gz (182kB)
    100% |████████████████████████████████| 184kB 906kB/s 
```

但是在使用的时候报错了：
```
root@ubuntu:~# python
Python 2.7.6 (default, Jun 22 2015, 17:58:13) 
[GCC 4.8.2] on linux2
Type "help", "copyright", "credits" or "license" for more information.
>>> import pycurl
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
ImportError: /usr/local/lib/libcurl.so.4: undefined symbol: SSLv2_client_method
``` 

## undefined symbol: SSLv2_client_method
这个错误一般说法就不一样了，有的说是因为编译curl的时候，需要加上nossl的选项，这样就不用关心ssl了。还有的说是ubuntu从11版本就不支持openssl 1.0了，需要自己编译openssl到2.0版本。这些都太麻烦了，我基本都是使用apt安装的，ubuntu系统应该都提供好了，应该不会犯这么严重的错误，毕竟curl还是比较常用的。

同时发现运行curl命令时出现下面的错误：
```
root@ubuntu:~# curl 
curl: /usr/local/lib/libcurl.so.4: no version information available (required by curl)
curl: try 'curl --help' or 'curl --manual' for more information

```

## no version information available
根据查找这个错误，终于找到一个简单的解决办法。原因是由于安装的libcurl.so是4.3版本，而软连接还在使用4.2版本。所以改一下链接就行了。
```
ls -l /usr/local/lib/libcurl.so.4
```
结果为：
```
lrwxrwxrwx 1 root root 16 Aug 16 21:15 /usr/local/lib/libcurl.so.4 -> libcurl.so.4.2.0
```
改成：
```
sudo rm -rf /usr/local/lib/libcurl.so.4
sudo ln -s /usr/lib/x86_64-linux-gnu/libcurl.so.4.3.0 /usr/local/lib/libcurl.so.4
```

最后输入curl命令，一切都正常了。
:-)

参考：
<https://stackoverflow.com/questions/30017397/error-curl-usr-local-lib-libcurl-so-4-no-version-information-available-requ>


