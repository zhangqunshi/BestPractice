#!/bin/bash
#
# Backup the data of the MySQL.
#
# STEP 1: chmod +x mysql_backup.sh
# STEP 2: vi /etc/crontab
# STEP 3: 
#         min hour day month week  user command
#         0   1    *   *     *     root /root/mysql_backup.sh
# STEP 4: /etc/init.d/crond restart
#
BACKUP_DIR=/var/log/mysql_backup
LOG_TIME=`date +%Y%m%d%H`
MYSQL_CMD_PATH=/usr/bin
DB_NAME=mydb
DB_USR=root
DB_PWD

$MYSQL_CMD_PATH/mysqldump -u $DB_USR -p$DB_PWD $DB_NAME | gzip > ${BACKUP_DIR}/${DB_NAME}_${LOG_TIME}.sql.gz
