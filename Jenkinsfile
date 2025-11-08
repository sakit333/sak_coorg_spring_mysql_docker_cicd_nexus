pipeline {
    agent any

    tools {
        maven 'maven'
    }

    stages {
        stage('To Build Jar file'){
            steps{
                sh 'mvn clean package -Dskiptests'
            }
        }
    }
    post {
        success {
            echo "project is success"
        }
        failure {
            echo "project is failure"
        }
    }
}