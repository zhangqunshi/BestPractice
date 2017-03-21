Tomcat��Nginx������
===================
# ����
����ϵͳ��ubuntu 14.04.4 LTS


# ��װNginx
�����ַ�ʽ��һ����ʹ��apt-get��������װ�����ư汾������һ��������Դ����Լ����롣

## �����ư�װ (��Ҫ����)
���ְ�װ��ʽ�Ƚϼ򵥡�ֱ�����У�
```
sudo apt-get install nginx
```
:) ��ĺ÷���ѽ�����ǰ汾�е�ͣ�nginx/1.4.6

��װ���ļ���Ŀ¼λ�ã�
1. �����ַ��/etc/init.d/nginx
2. ���õ�ַ��/etc/nginx/�����磺/etc/nginx/nginx.conf
3. WebĬ��Ŀ¼��/usr/share/nginx/http/�����磺usr/share/nginx/index.html
4. ��־Ŀ¼��/var/log/nginx/�����磺/var/log/nginx/access.log
5. �������ļ���/usr/sbin/nginx


## Դ���밲װ (���Բ�����)

### ����Nginx

�����ڹ�������������汾��http://nginx.org/en/download.html��

Ŀǰ��ʹ�õİ汾��Linux���������ȶ���1.10.3��

������Ϻ󣬽�ѹnginx-1.10.3.tar.gz��

```bash
wget http://nginx.org/download/nginx-1.10.3.tar.gz
tar zxvf nginx-1.10.3.tar.gz
```

TODO: ����



# ����Nginx
���Ƚ���confĿ¼`cd /etc/nginx`�����޸�nginx����֮ǰ����ԭʼ�����ļ�����һ�¡�
Ŀ¼�����ļ�Ϊ��

    # ls -l
    total 64
    drwxr-xr-x 2 root root 4096 Oct 27 23:38 conf.d
    -rw-r--r-- 1 root root  911 Mar  5  2014 fastcgi_params
    -rw-r--r-- 1 root root 2258 Mar  5  2014 koi-utf
    -rw-r--r-- 1 root root 1805 Mar  5  2014 koi-win
    -rw-r--r-- 1 root root 2085 Mar  5  2014 mime.types
    -rw-r--r-- 1 root root 5287 Mar  5  2014 naxsi_core.rules
    -rw-r--r-- 1 root root  287 Mar  5  2014 naxsi.rules
    -rw-r--r-- 1 root root  222 Mar  5  2014 naxsi-ui.conf.1.4.1
    -rw-r--r-- 1 root root 1601 Mar  5  2014 nginx.conf
    -rw-r--r-- 1 root root  180 Mar  5  2014 proxy_params
    -rw-r--r-- 1 root root  465 Mar  5  2014 scgi_params
    drwxr-xr-x 2 root root 4096 Mar 21 12:02 sites-available
    drwxr-xr-x 2 root root 4096 Mar 21 12:02 sites-enabled
    -rw-r--r-- 1 root root  532 Mar  5  2014 uwsgi_params
    -rw-r--r-- 1 root root 3071 Mar  5  2014 win-utf


conf.dĿ¼�����������ã���һ�㲻�á�
nginx.conf���Ѿ�include��site-enabled����������ļ���

> include /etc/nginx/conf.d/*.conf;
> include /etc/nginx/sites-enabled/*;

���Բ�Ҫ��nginx.conf�ļ��ˣ�ֱ���޸�site-enabled�е������ļ���
sites-availableĿ¼�е��ļ���site-enabled�е������ļ���ͬһ���ļ���
> lrwxrwxrwx 1 root root 34 Mar 21 12:02 default -> /etc/nginx/sites-available/default

�����޸�����Ŀ¼�µ�default�ļ���һ����

�޸�vim�����޸������ļ�: 
`vim /etc/nginx/sites-available/default`

�༭location���֣�

    location / {
        # First attempt to serve request as file, then
        # as directory, then fall back to displaying a 404.
        #try_files $uri $uri/ =404;
        # Uncomment to enable naxsi on this location
        # include /etc/nginx/naxsi.rules

        proxy_pass http://localhost:8080;
    }

    location ~ \.(gif|jpg|jpeg|png|bmp|swf)$ {
        root /opt/apache-tomcat-8.5.8/webapps/ROOT;
        expires 30d;
    }



# ����Nginx����
�޸����ú�������Ч���������
```
sudo service nginx restart
```


# �ο��ĵ�
��װ�ĵ�: <http://nginx.org/en/docs/install.html>
ʹ���ĵ�: <http://nginx.org/en/docs/beginners_guide.html>
