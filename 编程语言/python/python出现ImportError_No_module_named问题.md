pythonģ���Լ��������ImportError: No module named 'xxx'����
================

������ CentOS7

# ��������
ǰ�������ImportError: No module named 'xxx'���⡣�����ļ���������__init__.py�ļ���
˵�����Ѿ���ȷ��ͬʱ��Դ����Ŀ¼���뵽��PYTHONPATH���������С�������Ȼ����ʹ��û���ҵ�ԭ��
����·������飬ȷ������ȷ�ġ���������ϵͳ������û����ã����Բ�������ϵͳ��

## ����취
��������һ���취��ʹ��`sys.path.append()`�����·�����������Ϊ��
```
import sys
sys.path.append("/path/your/code")
```

# ��������
��ʵ�����ַ�����
- ʹ��PYTHONPATH��������
- ��py�ļ��ŵ�site-packagesĿ¼��
- ʹ��pth�ļ����ŵ�site-packagesĿ¼�¡���һ��һ��·����
- ����sys.path.append("path")
- ֱ�Ӱ�ģ���ļ��ŵ�$python_dir/libĿ¼��

���п�����Ȩ�����⣬��Щ��������root�£�����Щ�������ǵ������ԭ�����⻹�п�����__init__.py�ļ�����windowsϵͳ�������ַ����¡�

----------
�ο�: <http://blog.csdn.net/damotiansheng/article/details/43916881>
