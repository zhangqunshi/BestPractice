inotify����upper limit on inotify watches reached
====


�ڶ�һ������̽���inotify����ʱ���������´���
Failed to watch /mnt/;
upper limit on inotify watches reached!
Please increase the amount of inotify watches allowed per user via `/proc/sys/fs/inotify/max_user_watches��.

catһ������ļ���Ĭ��ֵ��8192��echo 8192000 > /proc/sys/fs/inotify/max_user_watches����~

