properties([
    buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), 
    parameters([
        choice(
            choices: ['main', 'widget'], 
            name: 'MODULE')
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
        image = docker.build("whoisyeshua/monorepo-${env.MODULE}", "--build-args MODULE=${env.MODULE}")

    }

    stage('Push Docker Image') {
        docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-whoisyeshua') {
            image.push("${env.BUILD_NUMBER}")
            image.push("latest")
        }
    }
}
