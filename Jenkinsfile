pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Sprawdź czy Jenkins ma skonfigurowane poświadczenia do repo
                // Jeśli repo jest publiczne, wystarczy samo url
                git url: 'https://github.com/bisekw/t_mobile_req.git', branch: 'master'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean test -Dgroups=API -D"Surefire.suiteXmlFiles=src/test/java/TestNg.xml" -DIS_REMOTE_RUN=true -Denvironment=UAT'
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
