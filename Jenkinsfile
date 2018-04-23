pipeline {
  agent {
    node {
      label 'maven'
    }
    
  }
  stages {
    stage('Deploy &  Test') {
      steps {
        sh 'mvn fabric8:deploy -Popenshift -DskipTests'
      }
    }
  }
}