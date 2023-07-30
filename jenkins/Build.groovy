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
        git branch: 'master', 
        url: 'git@github.com:whoisYeshua/monorepo-example.git',
        credentialsId: 'jenkins-github-monorepo-example'
    }

    // stage('Check Source Code') {
    //     // Print all files and directories in the current workspace
    //     sh 'ls -l'
    // }

    def image = null

    // stage('Check Docker version') {
    //     sh 'docker --version'
    // }

    stage('Build Docker Image') {
        // The Dockerfile is in the current directory
        image = docker.build("whoisyeshua/monorepo-${env.MODULE}", "--build-arg MODULE=${env.MODULE} .")

    }

    stage('Push Docker Image') {
        docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-whoisyeshua') {
            image.push("${env.BUILD_NUMBER}")
            image.push("latest")
        }
    }

    stage('Push Git tag') {
        sh """
        git config user.name 'jenkins-agent'
        git config user.email 'jenkins-agent@users.noreply.github.example.com'
        """

        def releaseTag = "release/${env.MODULE}-v${env.BUILD_NUMBER}"

        sh """
        git tag -a ${releaseTag} -m 'Tagging at Jenkins build ${releaseTag}'
        git push origin ${releaseTag}
        """
    }
}
