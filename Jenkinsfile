pipeline {
    agent any
    tools {
        maven 'maven'
    }
    parameters {
        string(name: 'DOCKER_IMAGE_NAME', defaultValue: 'coorg_sak_spring', description: 'Docker image name')
        choice(name: 'DEPLOY_ENV', choices: ['dev', 'prod'], description: 'Select the deployment environment')
        choice(name: 'ACTION', choices: ['deploy', 'remove'], description: 'Select the action')
        string(name: 'DOCKER_TAG', defaultValue: "${env.BUILD_ID}", description: 'Docker image tag')
        string(name: 'DOCKERHUB_USERNAME', defaultValue: 'sakit333', description: 'Docker Hub Username')
    }
    environment {
        DOCKERHUB_USERNAME = "${params.DOCKERHUB_USERNAME}"
        DOCKER_IMAGE = "${params.DOCKER_IMAGE_NAME}"
        DOCKER_VERSION_TAG = "${params.DOCKER_TAG}"
    }
    stages {
        stage('To Build Jar file'){
            when {
                allOf {
                    expression { params.DEPLOY_ENV == 'dev' }
                    expression { params.ACTION == 'deploy' }
                }
            }
            steps{
                sh 'mvn clean package -Dskiptests'
            }
        }
        stage('Build the Docker images') {
            when{
                allOf {
                    expression { params.DEPLOY_ENV == 'dev' }
                    expression { params.ACTION == 'deploy' }
                }        
            }
            steps {
                sh 'sudo docker build -t ${DOCKERHUB_USERNAME}/${DOCKER_IMAGE}:latest .'
            }
        }
        stage('Assign the Docker Tag with Latest version from the environment') {
            when {
                allOf {
                    expression { params.DEPLOY_ENV == 'dev' }
                    expression { params.ACTION == 'deploy' }
                }
            }
            steps {
                sh 'sudo docker tag ${DOCKERHUB_USERNAME}/${DOCKER_IMAGE}:latest ${DOCKERHUB_USERNAME}/${DOCKER_IMAGE}:${DOCKER_VERSION_TAG}'
            }
        }
        stage('Docker login from Jenkins credentials') {
            when {
                allOf {
                    expression { params.DEPLOY_ENV == 'dev' }
                    expression { params.ACTION == 'deploy' }
                }
            }
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable: 'DOCKERHUB_USER', passwordVariable: 'DOCKERHUB_PASS')]) {
                    sh 'echo $DOCKERHUB_PASS | sudo docker login -u $DOCKERHUB_USER --password-stdin'
                }
            }
        }
        stage('Push the Docker images to Docker Hub') {
            when {
                allOf {
                    expression { params.DEPLOY_ENV == 'dev' }
                    expression { params.ACTION == 'deploy' }
                }
            }
            steps {
                sh '''
                sudo docker push ${DOCKERHUB_USERNAME}/${DOCKER_IMAGE}:${DOCKER_VERSION_TAG}
                sudo docker push ${DOCKERHUB_USERNAME}/${DOCKER_IMAGE}:latest
                '''
            }
        }
        stage('Remove images Locally') {
            when {
                allOf {
                    expression { params.DEPLOY_ENV == 'dev' }
                    expression { params.ACTION == 'deploy' }
                }
            }
            steps {
                echo "Removing all the images Locally....!!!!"
                sh '''
                sudo docker rmi ${DOCKERHUB_USERNAME}/${DOCKER_IMAGE}:${DOCKER_VERSION_TAG} || echo "Image not found, skipping..."
                sudo docker rmi ${DOCKERHUB_USERNAME}/${DOCKER_IMAGE}:latest || echo "Image not found, skipping..."
                '''
            }
        }
        stage('Docker Logout from Jenkins') {
            when {
                allOf {
                    expression { params.DEPLOY_ENV == 'dev' }
                    expression { params.ACTION == 'deploy' }
                }
            }
            steps {
                sh 'sudo docker logout'
            }
        }
        stage('Deploy to the Dev Environment') {
            when {
                allOf {
                    expression { params.DEPLOY_ENV == 'dev' }
                    expression { params.ACTION == 'deploy' }
                }
            }
            steps {
                echo "Deploying to the Dev Environment....!!!!"
                sh 'sudo docker run -d --name spring_app_container --network mysql-network -p 8088:8088 ${DOCKERHUB_USERNAME}/${DOCKER_IMAGE}:latest'
            }
        }
        stage('Remove the container from Dev Environment') {
            when {
                allOf {
                    expression { params.DEPLOY_ENV == 'dev' }
                    expression { params.ACTION == 'remove' }
                }
            }
            steps {
                echo "Removing the container from Dev Environment....!!!!"
                sh '''
                sudo docker stop spring_app_container || echo "Container not running, skipping..."
                sudo docker rm spring_app_container || echo "Container not found, skipping..."
                '''
            }
        }
        stage('Remove the docker images') {
            when {
                allOf {
                    expression { params.DEPLOY_ENV == 'dev' }
                    expression { params.ACTION == 'remove' }
                }
            }
            steps {
                echo "Removing all the images Locally....!!!!"
                sh '''
                sudo docker rmi -f ${DOCKERHUB_USERNAME}/${DOCKER_IMAGE}:latest || echo "Image not found, skipping..."
                sudo docker rmi -f eclipse-temurin:17-jdk-alpine || echo "Image not found, skipping..."
                '''
            }
        }
        stage('Remove Jar Build') {
            when {
                allOf {
                    expression { params.DEPLOY_ENV == 'dev' }
                    expression { params.ACTION == 'remove' }
                }
            }
            steps {
                echo 'Removing Target Dir from the maven project'
                sh 'mvn clean'
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