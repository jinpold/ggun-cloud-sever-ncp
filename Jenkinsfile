pipeline {
    agent any
    environment {
        PUSH_VERSION = "latest"
        COMPOSE_TAGNAME = 'ggun'
        DOCKERHUB_CREDENTIALS = credentials('docker-token')
        services = "gateway,eureka,user,chat,alarm,admin,account"
    }

    stages {
        stage('Github clone') {
            steps {
                git branch: "main", credentialsId: 'github-token', url: "https://github.com/jinpold/ggun-cloud-sever-ncp.git"
            }
        }



        stage("Docker Login") {
            steps {
               sh "echo \$DOCKERHUB_CREDENTIALS_PSW | docker login -u \$DOCKERHUB_CREDENTIALS_USR --password-stdin"
            }
        }

        // 자바 필드
         stage('Java Build') {
            steps {
                script {
                    // gradlew에 실행 권한 부여
                    sh "chmod +x ./gradlew"

                    def servicesToBuild = [
                        "server:eureka-server",
                        "server:gateway-server",
                        "service:account-service",
                        "service:alarm-service",
                        "service:admin-service",
                        "service:chat-service",
                        "service:user-service"
                    ]

                    servicesToBuild.each { servicePath ->
                        sh "./gradlew :${servicePath}:bootJar"
                    }
                }
            }
        }



        stage("Docker Image Remove") {
            steps {
                script {
                    services.split(',').each { service ->
                        sh "docker rmi -f $COMPOSE_TAGNAME/${service}:$PUSH_VERSION"
                        sh "docker rmi -f $DOCKERHUB_CREDENTIALS_USR/${service}:$PUSH_VERSION"
                        sh "docker rmi -f $DOCKERHUB_CREDENTIALS_USR/ggun-${service}:$PUSH_VERSION"
                    }
                }
            }
        }

        stage("Docker Image Build") {
            steps {
                sh "docker compose build"
            }
        }

        stage("Docker Push") {
            steps {
                script {
                    services.split(',').each { service ->
                        sh "docker push $DOCKERHUB_CREDENTIALS_USR/${service}:$PUSH_VERSION"
                    }
                }
            }
        }

        // kube 배포
       stage('Deploy to Kubernetes') {
            steps {
                script {
                       sh "kubectl --kubeconfig=$HOME/kubeconfig.yaml get nodes"
                       sh 'kubectl --kubeconfig=$HOME/kubeconfig.yaml apply -f ./k8s/combined-service.yaml'
                       sh 'kubectl --kubeconfig=$HOME/kubeconfig.yaml apply -f ./k8s/combined-deployment.yaml'

                }
            }
       }

    }
}














//                        sh 'kubectl --kubeconfig=$HOME/kubeconfig.yaml apply -f ./k8s/ggun-deployment.yml'
//                        sh 'kubectl --kubeconfig=$HOME/kubeconfig.yaml apply -f ./k8s/ggun-service.yml'
//                        sh 'kubectl --kubeconfig=$HOME/kubeconfig.yaml delete pod -l app=ggun'