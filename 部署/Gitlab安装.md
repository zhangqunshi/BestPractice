GitLab安装
==========

环境: ubuntu16.04

## 安装依赖
```
sudo apt-get install curl openssh-server ca-certificates postfix
```

## 下载Gitlab的包并安装

```
curl -sS https://packages.gitlab.com/install/repositories/gitlab/gitlab-ce/script.deb.sh | sudo bash
sudo apt-get install gitlab-ce
```

## 配置并开始使用Gitlab
```
sudo gitlab-ctl reconfigure
```

安装真的很简单呀！

@完

-----------
参考：<https://about.gitlab.com/downloads/#ubuntu1604>

