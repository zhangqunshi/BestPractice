Python读取文件编码及内容
======

最近做一个项目，需要读取文件内容，但是文件的编码方式有可能都不一样。有的使用GBK，有的使用UTF8。所以在不正确读取的时候会出现如下错误：
```
UnicodeDecodeError: 'gbk' codec can't decode byte
```
而且当你使用rb模式读取文件时候，返回的结果通过django返回的json会出现下面错误：
```
TypeError: b'\xbc\x8c\xe6\x9c\xaa\xe6\x9d\xa5' is not JSON serializable
```
总之就是编码不对，所以要先能识别文件的编码方式，然后根据此编码方式进行对文件编码，最后返回文件内容。

解决方法如下：
```
with open("your_file", 'rb') as fp:
    file_data = fp.read()
    result = chardet.detect(file_data)
    file_content = file_data.decode(encoding=result['encoding'])
```
注： chardet是第一个第三方库，你需要自己使用pip进行安装。

@完