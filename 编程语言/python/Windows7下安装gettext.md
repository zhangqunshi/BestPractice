Windows7下安装gettext
====

## 首先下载gettext安装包:
<http://gnuwin32.sourceforge.net/packages/gettext.htm>

选择
```
GetText	0.14.4	 	library and tools for native language support

Binaries	 	Zip	 	1606131	 	6 May 2005	 	9e6b5499fa794ce6b8cebb8d9b850dd9
```

## 下载安装gettext-0.14.4-dep.zip

```
 Dependencies	 	Zip	 	715086	 	6 May 2005	 	b000047690d3030f231e160e2a6d101e
```

## 配置环境变量
解压上面的两个下载的zip包。
然后把里面的bin目录配置到环境变量的path中。

或者 也可以把dep包中的bin目录文件：
- libexpat.dll
- libiconv2.dll
复制到gettext目录下的bin目录中。这样就配置gettext-0.14.4-bin/bin到path中就可以了。



