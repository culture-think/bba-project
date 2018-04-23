pipeline {
  agent {
    node {
      label 'maven'
    }
    
  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean'
      }
    }
    stage('Deploy') {
      steps {
        sh 'mvn fabric8:deploy -Popenshift -DskipTests'
      }
    }
  }
}