nginx中root和alias的区别
=========

今天使用nginx搭建了一个网站，访问后出现404错误Not found. 上网查了一下原因，是由于nginx的配置不对。因为我是有两个web目录，这两个目录在不同的位置上。而且我不想把两个目录合并在一起，所以就要配置两个location。配置如下：

```
server {
	listen 80 default_server;
	listen [::]:80 default_server ipv6only=on;
	index index.html index.htm;

	# Make site accessible from http://localhost/
	server_name localhost;

	location / {
		root /www;
		# First attempt to serve request as file, then
		# as directory, then fall back to displaying a 404.
		try_files $uri $uri/ =404;
		# Uncomment to enable naxsi on this location
		# include /etc/nginx/naxsi.rules
	}

    location /website/ {
        root /var/lib/www;
        autoindex on;
    }
}
```

上面的配置浏览<http://localhost/website/>会显示404错误，因为root属性指定的值是要加入到最终路径的，所以访问的位置变成了`/var/lib/www/website/`。而我不想把访问的URI加入到路径中。所以就需要使用alias属性，其会抛弃URI，直接访问alias指定的位置, 所以最终路径变成`/var/lib/www/`。（最后需要加斜线）

```
    location /website/ {
        alias /var/lib/www;
        autoindex on;
    }
```

修改后重新加载配置（不用重启nginx）`nginx -s reload`（重启之前可以使用`nginx -t`命令检测一下配置是否正确），之后就可以访问<http://localhost/website/>试试了。 


@完

-----------
参考：<http://blog.csdn.net/u011510825/article/details/50531864>

