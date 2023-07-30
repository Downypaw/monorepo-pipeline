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

    stage('Check Docker version') {
        sh 'docker --version'
    }

    stage('Build Docker Image') {
        // The Dockerfile is in the current directory
        def image = docker.build("monorepo-${env.module}:${env.BUILD_NUMBER}")

        println('before registry')
        docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-whoisyeshua') {
            
            echo 'after build'
            image.push("${env.BUILD_NUMBER}")
            image.push("latest")
        }
    }
}
