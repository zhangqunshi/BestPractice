安装和部署Jenkins
===================
# 环境
操作系统：ubuntu 14.04.4 LTS

# 下载Jenkins
```
wget https://mirrors.tuna.tsinghua.edu.cn/jenkins/war-stable/2.32.3/jenkins.war
```

# 安装Jenkins
Jekins有三种方式进行安装：
- 可以通过本地的包文件直接安装
- 或者使用Docker进行安装；
- 还可以下载war文件，之后安装在一台带有JRE的机器上。

下面介绍的是最后一种安装方式。需要至少Java7以上的（推荐Java 8）环境。
至少具有512MB内存。
1. 下载jenkins.war。（上面已经说明了）
2. 通过SSH连接到ubuntu上，然后运行`java -jar jenkins.war`
3. 打开web浏览器，访问http://localhost:8080，接下来安装页面上的说明进行部署
4. 许多的Pipeline例子需要安装Docker。

# 建立Pipeline
最快的方式是，复制下面的例子到你的代码版本库中，并使用Jenkinsfile作为文件名。
1. 建立jenkinsfile

下面是针对Java语言的:
    Jenkinsfile (Declarative Pipeline)
    pipeline {
        agent { docker 'maven:3.3.3' }
        stages {
            stage('build') {
                steps {
                    sh 'mvn --version'
                }
            }
        }
    }

针对Python语言的:    
    Jenkinsfile (Declarative Pipeline)
    pipeline {
    agent { docker 'python:3.5.1' }
    stages {
        stage('build') {
            steps {
                sh 'python --version'
            }
        }
    }
}

2. 在Jenkins中点击*New Item*菜单
![jenkins_new_item](https://jenkins.io/doc/book/resources/pipeline/new-item-selection.png)

3. 给new item输入名称，并选择*Multibranch Pipeline*
4. 点击*Add Source*按钮, 选择仓库类型
5. 点击*Save*按钮来运行Pipeline。

# 参考

https://jenkins.io/doc/pipeline/tour/hello-world/
