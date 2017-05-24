#!/bin/sh
echo "stop django"
ps aux|grep "python manage.py runserver" | awk '{print $2}' |xargs kill
echo "OK"
sleep 3
echo "start django"
cd /your/project/path
python manage.py runserver > /var/log/django_console.log 2>&1 &
echo "OK"
