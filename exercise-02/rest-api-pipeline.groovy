node {

    stage('Notify') {
        sendNotification('Started')
    }

    def rootPath = 'exercise-03-rest-api-crud/app-rest-api'
    // tool `maven-3.6.1` needs to be defined in Manage Jenkins -> Global tools -> Maven
    def mavenHome = tool name: 'maven-3.6.1', type: 'maven'

    stage('Clone') {
        try {
            git 'https://github.com/pduleba/Docker-SL.git'
        } catch (all) {
            println("Unable to download sources from GitHUB")
            currentBuild.result = 'FAILURE'
            throw all
        }
    }

    stage('Clean') {
        dir(rootPath) {
            sh label: '', script: "${mavenHome}/bin/mvn clean"
        }
    }

    stage('Test') {
        dir(rootPath) {
            sh label: '', script: "${mavenHome}/bin/mvn test"
        }
    }

    stage('Install') {
        dir(rootPath) {
            sh label: '', script: "${mavenHome}/bin/mvn install -Dmaven.test.skip=true"
        }
    }

    stage('Reports') {
        dir(rootPath) {
            step([$class: 'JUnitResultArchiver', testResults: 'target/surefire-reports/TEST-*.xml'])
        }
    }

    stage('Archive') {
        dir(rootPath) {
            archiveArtifacts 'target/*.jar'
        }
    }
}

def sendNotification(status) {
    // just log status but it is possible to send e-mail via `emailext` module
    try {
        println("""${status}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'""")
    } catch (all) {
        println("ERROR :: Unable to send notification")
    }
}
