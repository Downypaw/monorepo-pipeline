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
}
