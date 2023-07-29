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

    stage('Use Source Code') {
        // Print all files and directories in the current workspace
        sh 'ls -l'
    }

    stage('Build Docker Image') {
        // Optional: Push the Docker image to a Docker registry
        docker.withRegistry('https://registry.hub.docker.com', '0f5d8e3e-e52e-472f-be6e-cc4e558cd32c') {
            // The Dockerfile is in the current directory
            def image = docker.build("monorepo-${env.module}:${env.BUILD_NUMBER}", '--build-args=${evn.module}')

            image.push("${env.BUILD_NUMBER}")
            image.push("latest")
        }
    }
}
