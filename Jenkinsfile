pipeline {
  agent {
    node {
      label 'maven'
    }
    
  }
  stages {
    stage('Deploy') {
      steps {
        sh 'mvn fabric8:deploy -Popenshift -DskipTests'
      }
    }
  }
}