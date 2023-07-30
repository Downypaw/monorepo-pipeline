properties([
    buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), 
    parameters([
        choice(
            choices: ['main', 'widget'], 
            name: 'module')
    ])
])

node {
    stage('Checkout') {
        git branch: 'feature/ci', url: 'https://github.com/whoisYeshua/monorepo-example.git'
    }

    stage('Check Source Code') {
        // Print all files and directories in the current workspace
        sh 'ls -l'
    }

    def image = null

    stage('Check Docker version') {
        sh 'docker --version'
    }

    stage('Build Docker Image') {
        // The Dockerfile is in the current directory
        image = docker.build("whoisyeshua/monorepo-${env.module}:${env.BUILD_NUMBER}")

    }

    stage('Push Docker Image') {
        docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-whoisyeshua') {
            image.push("whoisyeshua/${env.BUILD_NUMBER}")
            image.push("latest")
        }
    }
}
