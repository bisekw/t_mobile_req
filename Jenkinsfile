pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Sprawdź czy Jenkins ma skonfigurowane poświadczenia do repo
                // Jeśli repo jest publiczne, wystarczy samo url
                git url: 'https://github.com/USER/REPO.git', branch: 'main'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Report') {
            steps {
                junit 'target/surefire-reports/*.xml'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/surefire-reports/*.xml', fingerprint: true
        }
        failure {
            echo 'Testy nie powiodły się!'
        }
        success {
            echo 'Testy zakończone sukcesem!'
        }
    }
}
