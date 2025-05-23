pipeline {
  agent {label 'master'}
  environment {
IMAGE_NAME = ""
  }

    stages {
      stage ('Checkout'){
            steps{
             script {
               sh 'echo "skipping checkout as job take care of it"'
         //     checkout scm
              }
         }
        }
      stage ('Build and Test'){
       steps {
         script {
           sh 'ls -ltr'
           sh 'mvn clean package'
          }      
        }
      }
      stage ('Build and Push Docker image'){
         steps {
         script {
           sh 'echo "checking the build "'
          //  sh ' docker build -t $IMAGE_NAME .'
       }
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
