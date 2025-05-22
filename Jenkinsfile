pipeline {
  agent {label 'master'}
  environment {
    def DOCKER_TAG = "CS1-DEVpwww-${BUILD_NUMBER}"
    DOCKER_REGISTRY = "<docker_registry_URL>"
    DOCKER_REPO = "<docker_repo_name>"
    WEBHOOK_URL = credentials('My_Teams_notification')
  }

    stages {
      stage ('Checkout'){
            steps{
              withCredentials([gitUsernamePassword(credentialsId: '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', gitToolName: 'Default')]) {
              checkout scm
              }
            }
 }
}
}
