django�ж���ֶε�ģ����ѯ
========


ʹ��`Entity.objects.filter(name_contains='kris').filter(address='beijing')`

���������ָ���ְ���kris�����ҵ�ַ����beijing�ļ�¼��

����ǲ����ִ�Сд����ôʹ��icontains�滻contains.

���Ҫ�ĳɻ�Ļ�������ʹ��������ʽ:

```
Entity.objects.filter(Q(name_icontains='kris') | Q(address_icontains='beijing'))
```