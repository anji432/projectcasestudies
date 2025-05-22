pipeline {
  agent {label 'master'}
  environment {
    def DOCKER_TAG = "CS1-DEV-${BUILD_NUMBER}"
    DOCKER_REGISTRY = "<docker_registry_URL>"
    DOCKER_REPO = "<docker_repo_name>"
    WEBHOOK_URL = credentials('My_Teams_notification')
  }

    stages {
      stage ('Checkout'){
            steps{
              checkout scm
              }
           

post {
          always{
              script {
                  currentBuild.displayName = "${BUILD_NUMBER}-${NODE_NAME}/${DOCKER_TAG}"
              }
          }	
		
		 success{
              script {
                  emailext mimeType: 'text/html',
                  to: 'anji10432@gmail.com',
                  subject: "${BUILD_NUMBER}-${JOB_NAME}-${NODE_NAME}",
                  body: '${JELLY_SCRIPT,template="html"}'
              }
          }
      
          failure{
              script {
                  emailext mimeType: 'text/html',
                      to: 'anji10432@gmail.com',
                      subject: "${BUILD_NUMBER}-${JOB_NAME}-${NODE_NAME}",
                      body: '${JELLY_SCRIPT,template="html"}'
                  
              }
          }
	}
     }
   }
}
