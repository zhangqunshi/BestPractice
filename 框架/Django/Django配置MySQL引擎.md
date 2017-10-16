Django配置MySQL引擎
=====

Mysql5.6的在settings.py中使用：
```
'init_command': "SET sql_mode='STRICT_TRANS_TABLES',storage_engine=MyISAM"
```

Mysql5.7的在settings.py中使用：
```
'init_command': "SET sql_mode='STRICT_TRANS_TABLES',default_storage_engine=MyISAM"
```