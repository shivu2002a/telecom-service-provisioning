pipeline {
    agent any
    environment {
        PATH = "C:/Users/e039326/Downloads/Softwares/apache-maven-3.9.8-bin/apache-maven-3.9.8/bin;${env.PATH}"
    }
    stages {
        stage('1. Clone from github') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[url: 'https://github.com/shivu2002a/telecom-service-provisioning.git']]
                ])
            }
        }
        stage('2. List Files') {
            steps {
                bat "dir"
            }
        }
        stage("3. Maven clean compile") {
            steps {
                bat "mvn clean compile"
            }
        }
	    stage("4. Run the tests") {
            steps {
                bat "mvn test"
            }
        }
        stage("5. App packaging") {
            steps {
                bat "mvn package"
            }
        }
	    stage("6. Install the jar") {
            steps {
                bat "mvn install"
            }
        }
        stage("7. Build the docker image") {
            steps {
                bat "docker build -t telcoservice-backend ."
            }
        }
        stage("8. Remove running containers if exist") {
            steps {
                script {
                    def composeFilePath = "C:\\Users\\e039326\\Downloads\\Project\\telecom-service-provisioning\\docker-compose.yml"
                    
                    // Stop the Docker Compose services on Windows
                    echo "Stopping Docker Compose services..."
                    bat """
                        docker-compose -f ${composeFilePath} down
                    """
                }
            }
        }

        stage("9. Run the docker compose") {
            steps {
                bat "docker compose up -d"
            }
        }

    }
}