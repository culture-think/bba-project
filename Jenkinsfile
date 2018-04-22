pipeline {
  agent {
    node {
      label 'maven'
    }
    
  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean package'
      }
    }
    stage('Deploy & Test') {
      steps {
        sh 'mvn fabric8:deploy -Popenshift -DskipTests'
      }
    }
  }
}