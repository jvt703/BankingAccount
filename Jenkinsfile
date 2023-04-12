pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }


        stage('Docker Build') {
            steps {
                script {
                    sh 'docker build -t blazervincent/account-microservice .'
                }
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub-credentials-id') {
                        sh 'docker push blazervincent/account-microservice'
                    }
                }
            }
        }
    }
}