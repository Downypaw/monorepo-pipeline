npmRegistries = ['https://nexus-cd-ci.berpr.bemna.ru/repository/all-npm/']
dockerRepos = ['ci04499999/ci04499998']
modules = []
constants = []
node {
  stage('Get modules list') {
    constants = new Constants()

    modules = ['main', 'widget']
  }

  stage('Setup parameters') {
    properties([
      parameters([
        booleanParam(
          name: 'skip_run',
          description: 'Пропустить все стадии. Включить если нужно обновить конфиг',
          defaultValue: false
        ),
        extendedChoice(
          name: 'MODULE_NAMES',
          value: modules.join(','),
          defaultValue: 'main',
          visibleItemCount: 100,
          description: "Укажите модули для сборки (подгружает сюда директории из ${constants.pathToModules})",
          type: 'PT_CHECKBOX'
        ),
        listGitBranches(
          name: 'BRANCH_NAME',
          description: 'Ветка из которой выполнить сборку',
          credentialsId: 'jenkins-github-monorepo-example', 
          type: 'BRANCH',
          remoteURL: constants.repoSShUrl, 
          listSize: '0',
          quickFilterEnabled: true, 
          defaultValue: 'refs/heads/develop', 
          selectedValue: 'TOP'
        ),
        choice(
          name: 'NPM_REGISTRY',
          choices: npmRegistries,
          description: 'URL NPM репозитория'
        ),
        string(
          name: 'TYZ_CRED',
          defaultValue: 'params.expert.TYZ', 
          trim: true,
          description: 'Cred ID Ta'
        ),
        string(
          name: 'DOCKER_REGISTRY',
          defaultValue: 'nexus-cd-ci.berpr.bemna.ru',
          trim: true,
          description: 'Docker registry address. "image registry" in Helm values'
        ),
        choice(
          name: 'DOCKER_REPOSITORY', 
          choices: dockerRepos, 
          description: 'Docker repository for docker image."Image.repository" in Helm values',
        ), 
        string(
          name: 'PROJECT_NAME',
          defaultValue: 'ci04499999-des',
          trim: true,
          description: "Наименование проекта для docker image (без номера) (добавится наименование модуля, e.g. -efs-main)",
        ),
        string(
          name: 'DOCKER_TAG_PREFIX',
          defaultValue: '',
          trim: true,
          description: "Префикс тега для docker image, через дефис добавится номер сборки, например prefix-123"
        ),
        string(
          name: 'DOCKER_TAG', 
          defaultValue: '',
          trim: true,
          description: "Полное переопределение тега для docker image (оставить пустым - подставит префикс + номер сборки)"
        )
      ])
    ])
  }

  stage('Echo parameters') {
    println("MODULE_NAMES: ${env.MODULE_NAMES}")
    println("MODULE_NAMES: ${MODULE_NAMES}")

    if (MODULE_NAMES == '') {
      throw new Exception("Empty MODULE_NAMES")
    }

    def readableBranchName = BRANCH_NAME.minus('refs/heads/')
    currentBuild.displayName = "${MODULE_NAMES}-${readableBranchName}"
  }
}

class Constants {
  def pathToModules = 'apps'
  def repoSShUrl = 'https://github.com/whoisYeshua/monorepo-example'
}