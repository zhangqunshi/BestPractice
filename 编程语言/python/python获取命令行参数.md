python获取命令行参数
====

主要是通过sys的argv列表来获取命令行内容，命令行的参数以空格分隔放到argv列表中。

```python
import sys

if __name__ == "__main__":
    if len(sys.argv) > 0:
        print(sys.argv[0])
        
    if len(sys.argv) > 1:
        print(sys.argv[1])
```


@完
