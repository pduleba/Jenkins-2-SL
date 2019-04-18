stage('Manual Stage (flyweight stage)') {
    // * `flyweight executor` - DO NOT limits available executors - running as separate thread
    input message: 'Start?',
            ok: 'Yes.',
            concurrency: 1
}


// parallel integration (on node labeled `master-label`)
node('master-label') {

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

    stage('Clean/Test') {
        dir(rootPath) {
            sh label: '', script: "${mavenHome}/bin/mvn clean test"
        }
    }

    stage('Install') {
        dir(rootPath) {
            sh label: '', script: "${mavenHome}/bin/mvn install -Dmaven.test.skip=true"
        }
    }

    // save data in this node (to be passed between nodes)
    stage('Stash') {
        dir(rootPath) {
            stash name: 'archive-package',
                    includes: 'target/*.jar'
        }
    }
}

// parallel integration (ANY NODE - no node specified)
stage('Parallel Stage') {
    parallel ls: {
        executeCommand('Thread-1', 'whoami')
    }, pwd: {
        executeCommand('Thread-2', 'whoami')
    }, whoami: {
        executeCommand('Thread-3', 'whoami')
    }
}

// forward further execution to node labeled `windows-label`
node('windows-label') {

    stage('Unstash') {
        // clean up old data
        sh 'rm -rf *'
        // load data passed between nodes (can be the same node as source)
        unstash 'archive-package'
    }

    stage('Archive') {
        archiveArtifacts 'target/*.jar'
    }
}

node {
    stage('Manual Stage (heavyweight stage)') {
        // * `heavyweight executor` - limits available executors
        input message: 'Finish?',
                ok: 'Yes.',
                concurrency: 1
        sh 'echo "Heavyweight Stage - COMPLETE"'
    }
}

def executeCommand(executionName, command) {
    // ANY NODE available
    node {
        sh "echo ${executionName}"
        sh command
    }
}
