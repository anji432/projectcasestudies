
pipeline {
  agent any
  environment {
    IMAGE_NAME = "anji432/veerarepo:latest"
    MANIFEST_PATH = "manifest_file/k8s"
  }

  stages {
    stage('Checkout') {
      steps {
        git branch: 'main', url: 'https://github.com/anji432/projectcasestudies.git'
      }
    }

    stage('Build and Test') {
      steps {
        sh 'ls -ltr'
        sh 'mvn clean package'
      }
    }

    stage('Build and Push Docker Image') {
      steps {
        script {
          sh 'docker build -t $IMAGE_NAME .'
        }
      }
    }

    stage('Push to DockerHub') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'DockerHub-Credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
           sh '''
            echo "$DOCKER_PASS" | docker login -u "anji432" --password-stdin
            docker push $IMAGE_NAME
          '''
        }
      }
    }

   /* stage('Static Code Analysis') {
      environment {
        SONAR_URL = "http://18.116.40.31:9000"
      }
      steps {
        withCredentials([string(credentialsId: 'sonarqube', variable: 'SONAR_AUTH_TOKEN')]) {
          sh '''
            mvn sonar:sonar \
              -Dsonar.login=$SONAR_AUTH_TOKEN \
              -Dsonar.host.url=$SONAR_URL
          '''
        }
      }
    }*/

    stage('Deploy to Dev') {
      steps {
          withKubeCredentials(kubectlCredentials: [[caCertificate: '', clusterName: 'veera-cluster.ap-south-1.eksctl.io', contextName: '', credentialsId: 'k8_secret_token', namespace: '', serverUrl: 'https://7E5A221BDABEC23E3E1C11D40BFDF608.gr7.ap-south-1.eks.amazonaws.com']]) {
		  sh 'curl -LO "https://storage.googleapis.com/kubernetes-release/release/v1.20.5/bin/linux/amd64/kubectl"'  
          sh 'chmod u+x ./kubectl'  
          sh './kubectl apply -f ${MANIFEST_PATH}/dev/deployment.yaml --namespace=dev'
		  sh './kubectl rollout status deployment/spring-boot-app --namespace=dev'
            }
        }
    }

    stage('Deploy to Test') {
      steps {
        withKubeCredentials(kubectlCredentials: [[caCertificate: '', clusterName: 'veera-cluster.ap-south-1.eksctl.io', contextName: '', credentialsId: 'k8_secret_token', namespace: '', serverUrl: 'https://7E5A221BDABEC23E3E1C11D40BFDF608.gr7.ap-south-1.eks.amazonaws.com']]) {
		  sh 'curl -LO "https://storage.googleapis.com/kubernetes-release/release/v1.20.5/bin/linux/amd64/kubectl"'  
          sh 'chmod u+x ./kubectl'  
          sh './kubectl apply -f ${MANIFEST_PATH}/test/deployment.yaml --namespace=test'
		sh './kubectl apply -f ${MANIFEST_PATH}/test/service.yaml --namespace=test'
		  sh './kubectl rollout status deployment/spring-boot-app --namespace=test'
            }
      }
    }

    stage('Approval to Deploy to Prod') {
      steps {
        script {
          input message: "Approve deployment to Prod?", parameters: [
            booleanParam(name: 'Proceed', defaultValue: false, description: 'Approve the deployment to Prod')
          ]
        }
      }
    }

    stage('Deploy to Prod') {
      when {
        expression { return params.Proceed == Proceed }
      }
      steps {
       withKubeCredentials(kubectlCredentials: [[caCertificate: '', clusterName: 'veera-cluster.ap-south-1.eksctl.io', contextName: '', credentialsId: 'k8_secret_token', namespace: '', serverUrl: 'https://7E5A221BDABEC23E3E1C11D40BFDF608.gr7.ap-south-1.eks.amazonaws.com']]) {
		  sh 'curl -LO "https://storage.googleapis.com/kubernetes-release/release/v1.20.5/bin/linux/amd64/kubectl"'  
          sh 'chmod u+x ./kubectl'  
          sh './kubectl apply -f ${MANIFEST_PATH}/prod/deployment.yaml --namespace=prod'
		  sh './kubectl rollout status deployment/spring-boot-app --namespace=prod'
            }
      }
    }
  }

  post {
    success {
      echo 'Deployment successful!'
      mail to: 'anji10432@gmail.com',
           subject: "Jenkins Pipeline Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
           body: "Good news! Jenkins job '${env.JOB_NAME}' (build #${env.BUILD_NUMBER}) completed successfully.\n\nCheck details: ${env.BUILD_URL}"
    }

    failure {
      echo 'Deployment failed!'
      mail to: 'anji10432@gmail.com',
           subject: "Jenkins Pipeline Failure: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
           body: "Oops! Jenkins job '${env.JOB_NAME}' (build #${env.BUILD_NUMBER}) failed.\n\nCheck details: ${env.BUILD_URL}"
    }
  }
}
