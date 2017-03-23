��װ�Ͳ���Jenkins
===================
# ����
����ϵͳ��ubuntu 14.04.4 LTS

# ����Jenkins
```
wget https://mirrors.tuna.tsinghua.edu.cn/jenkins/war-stable/2.32.3/jenkins.war
```

# ��װJenkins
Jekins�����ַ�ʽ���а�װ��
- ����ͨ�����صİ��ļ�ֱ�Ӱ�װ
- ����ʹ��Docker���а�װ��
- ����������war�ļ���֮��װ��һ̨����JRE�Ļ����ϡ�

������ܵ������һ�ְ�װ��ʽ����Ҫ����Java7���ϵģ��Ƽ�Java 8��������
���پ���512MB�ڴ档
1. ����jenkins.war���������Ѿ�˵���ˣ�
2. ͨ��SSH���ӵ�ubuntu�ϣ�Ȼ������`java -jar jenkins.war`
3. ��web�����������http://localhost:8080����������װҳ���ϵ�˵�����в���
4. ����Pipeline������Ҫ��װDocker��

# ����Pipeline
���ķ�ʽ�ǣ�������������ӵ���Ĵ���汾���У���ʹ��Jenkinsfile��Ϊ�ļ�����
1. ����jenkinsfile

���������Java���Ե�:
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

���Python���Ե�:    
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

2. ��Jenkins�е��*New Item*�˵�
![jenkins_new_item](https://jenkins.io/doc/book/resources/pipeline/new-item-selection.png)

3. ��new item�������ƣ���ѡ��*Multibranch Pipeline*
4. ���*Add Source*��ť, ѡ��ֿ�����
5. ���*Save*��ť������Pipeline��

# �ο�

https://jenkins.io/doc/pipeline/tour/hello-world/
