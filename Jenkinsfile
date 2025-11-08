pipeline {
    agent any

    stages {
        stage('To check'){
            steps{
                sh """
                pwd
                whoami
                ls -l
                echo $BUILD_ID
                echo $JOB_NAME
                """
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