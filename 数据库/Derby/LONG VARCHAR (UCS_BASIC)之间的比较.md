java.sql.SQLSyntaxErrorException 不支持“LONG VARCHAR (UCS_BASIC)”与“LONG VARCHAR (UCS_BASIC)”之间的比较
====

Interestingly, this is not possible:
select 1 from SYSIBM.SYSDUMMY1 where cast('2' as long varchar) = cast('2' as long varchar)
Whereas these statements are executable:
select 1 from SYSIBM.SYSDUMMY1 where '2' = '2'
select 1 from SYSIBM.SYSDUMMY1 where cast(cast('2' as long varchar) as varchar(1)) = cast(cast('2' as long varchar) as varchar(1))
According to the documentation that is the correct behaviour:
http://db.apache.org/derby/docs/10.7/ref/rrefsqlj58560.html
Nevertheless, if casting is possible between LONG VARCHAR and VARCHAR, and assignment too, then I don't understand why LONG VARCHAR cannot even be compared to LONG VARCHAR
Note: A similar issue has been open for a long time:
https://issues.apache.org/jira/browse/DERBY-342



参考：
<https://issues.apache.org/jira/browse/DERBY-5130>