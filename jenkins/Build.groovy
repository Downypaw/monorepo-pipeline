properties([
    buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), 
    parameters([
        choice(
            choices: ['main', 'widget'], 
            name: 'module')
    ])
])

def withError = null

node {
    stage('Show Parameters') {
        echo "module: ${env.module}"
    }

    stage('Print currentBuild') {
        println("number: ${currentBuild.number}")
        println("result: ${currentBuild.result}")
        println("currentResult: ${currentBuild.currentResult}")
        println("displayName: ${currentBuild.displayName}")
        println("fullDisplayName: ${currentBuild.fullDisplayName}")
        println("projectName: ${currentBuild.projectName}")
        println("fullProjectName: ${currentBuild.fullProjectName}")
        println("description: ${currentBuild.description}")
        println("id: ${currentBuild.id}")
        println("timeInMillis: ${currentBuild.timeInMillis}")
        println("startTimeInMillis: ${currentBuild.startTimeInMillis}")
        println("duration: ${currentBuild.duration}")
        println("durationString: ${currentBuild.durationString}")
        println("previousBuild: ${currentBuild.previousBuild}")
        println("previousBuildInProgress: ${currentBuild.previousBuildInProgress}")
        println("previousBuiltBuild: ${currentBuild.previousBuiltBuild}")
        println("previousCompletedBuild: ${currentBuild.previousCompletedBuild}")
        println("previousFailedBuild: ${currentBuild.previousFailedBuild}")
        println("previousNotFailedBuild: ${currentBuild.previousNotFailedBuild}")
        println("previousSuccessfulBuild: ${currentBuild.previousSuccessfulBuild}")
        println("nextBuild: ${currentBuild.nextBuild}")
        println("absoluteUrl: ${currentBuild.absoluteUrl}")
        println("buildVariables: ${currentBuild.buildVariables}")
    }

    stage('Echo env') {
        sh 'printenv'
    }

    stage('Node Version') {
        try {
            sh 'node --version'
        } 
        catch (exc) {
            echo 'Something failed, I should sound the klaxons!'
            withError = true
        }
    }

    stage('Check Previous') {
        println(withError)
    }
}
