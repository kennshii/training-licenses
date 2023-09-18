def qualitygate
pipeline {
    agent {
        kubernetes {
            defaultContainer 'jnlp'
            yamlFile 'KubernetesPod.yaml'
        }
    }
    environment {
        TL_PGSQL_URL=credentials('TL-PGSQL_URL')
        TL_PGSQL_DATABASE=credentials('TL-PGSQL_DATABASE')
        TL_PGSQL_USERNAME=credentials('TL-PGSQL_USERNAME')
        TL_PGSQL_PASSWORD=credentials('TL-PGSQL_PASSWORD')
        MSTEAMS_WEBHOOK=credentials('MS_TEAMS_WEBHOOK')
    }
    options {
        buildDiscarder logRotator(daysToKeepStr: '1', numToKeepStr: '10')
        office365ConnectorWebhooks([
            [name: "Office 365",
             notifyBackToNormal: true, 
             notifyFailure: true,
             notifyRepeatedFailure: true,
             notifySuccess: true]
        ])
    }
    tools {
        maven 'maven-3.9'
    }
    stages {
        stage('SonarQube Analysis') {
            steps {
                container('custom-ubuntu') {
                    script {
                        withSonarQubeEnv(credentialsId: 'sonarqube-tl-token', installationName: 'SonarQube-TL') {
                            sh """
                                mvn clean verify sonar:sonar -Dsonar.coverage.exclusions=**/application/dto/*.java,**/domain/entity/*.java,**/domain/cache/*.java,**/rest/config/*.java
                            """
                        }
                    }
                }
            }
        }
        stage("Quality gate") {
            steps {
				script {
                	qualitygate = waitForQualityGate(credentialsId: 'sonarqube-tl-token')
                	echo "${qualitygate.status}"
      				if (qualitygate.status != "OK") {
         				error "Pipeline aborted due to quality gate coverage failure: ${qualitygate.status}"
      				}
				}
            }
        }
        stage('Build Docker Imagine') {
            when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }
            steps {
                echo "Build docker imagine..."
                container('dind') {
                    script {
                        git branch: 'main',
                            credentialsId: 'c44036e4-f223-43ec-8568-3ac21ae7f0e5',
                            url: 'https://gitlab.tool.mddinternship.com/training-licenses/training-licenses.git'

                        sh '''awk -F"[<>]" '/<version>[0-9]+\\.[0-9]+\\.[0-9]+-SNAPSHOT<\\/version>/{gsub("-SNAPSHOT", "", $3); print $3; exit}' pom.xml > ./temp.txt'''
                        env.appVersion = readFile('./temp.txt').trim()
                        app = docker.build("training-licenses:${appVersion}")
                    }
                }
            }
        }
        stage('Push to Nexus') {
            when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }
            steps {
                container('dind') {
                    script {
                        docker.withRegistry('https://nexus.tool.mddinternship.com', 'nexus-jenkins') {
                            app.push("${appVersion}")
                        }
                    }
                }
            }
        }
        stage('Set EKS Cluster') {
            when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }
            steps {
                container('custom-ubuntu') {
                    script {
                        withAWS(credentials: 'vicalmic-aws-credentials', region: 'eu-central-1') {
                            sh """
                                aws eks update-kubeconfig --name internship-eks --region eu-central-1
                                kubectl get nodes 
                            """
                        }
                    }
                }
            } 
        }   
        stage('Deploy') {
             when {
                expression {
                    BRANCH_NAME == 'main'
                }
            }
            steps {
                container('custom-ubuntu') {
                    script {
                        dir('helm') {
                            git branch: 'main',
                                credentialsId: 'gitlab-tl-helm',
                                url: 'https://gitlab.tool.mddinternship.com/root/tl-backend-chart.git'
                        }
                        withAWS(credentials: 'vicalmic-aws-credentials', region: 'eu-central-1') {
                            echo "${appVersion}"
                            sh """
                                kubectl config set-context --current --namespace=application
                                helm package ./helm
                                helm upgrade -i --set image.tag=${env.appVersion} --set PGSQL_URL=${TL_PGSQL_URL} --set PGSQL_DATABASE=${TL_PGSQL_DATABASE} --set PGSQL_USERNAME=${TL_PGSQL_USERNAME} --set PGSQL_PASSWORD=${TL_PGSQL_PASSWORD} tl-backend tl-backend-*.tgz
                            """                         
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            office365ConnectorSend ( webhookUrl: "${MSTEAMS_WEBHOOK}",
                message: """ 
                    Training Licenses Backend v${env.appVersion}.
                    Jenkins Build ${env.BUILD_NUMBER}: "${currentBuild.currentResult}"
                    Jenkins build ID: ${env.BUILD_ID}
                    SonarQube status: ${qualitygate.status}
                """
            ) 
        }
    }
}