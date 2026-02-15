// Jenkinsfile (Declarative Pipeline) - projetado para o projeto Gradle + Selenide
// Observações:
// - Usa ./gradlew --no-daemon
// - Publica JUnit results e arquiva artifacts úteis
// - Controle por parâmetros (HEADLESS, BASE_URL, BROWSER, SELENIDE_AUTOOPEN)
// - Mantém segredos fora do Jenkinsfile (use Credentials/Secrets no Jenkins)

pipeline {
  agent { label 'linux' }

  options {
    // build timeout total
    timeout(time: 60, unit: 'MINUTES')
    // prefixa logs com timestamp
    timestamps()
    // coloriza output (requer plugin AnsiColor)
    ansiColor('xterm')
    // manter histórico de builds
    buildDiscarder(logRotator(numToKeepStr: '50'))
  }

  parameters {
    booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Executar navegador em modo headless')
    string(name: 'BASE_URL', defaultValue: 'https://www.saucedemo.com', description: 'URL base para os testes')
    choice(name: 'BROWSER', choices: ['chrome','firefox'], description: 'Navegador para executar os testes')
    booleanParam(name: 'SELENIDE_AUTOOPEN', defaultValue: true, description: 'Se true, BrowserConfig abrirá automaticamente a base URL')
    string(name: 'CHROME_VERSION', defaultValue: '', description: 'Versão do Chrome a instalar no agente (opcional)')
    booleanParam(name: 'USE_DOCKER', defaultValue: false, description: 'Se true, tenta executar dentro de um container Docker (requer configuração adicional)')
    booleanParam(name: 'PUBLISH_ALLURE', defaultValue: false, description: 'Se true, tenta publicar relatório Allure (requer plugin Allure no Jenkins)')
  }

  environment {
    // força o gradle wrapper a não usar daemon
    GRADLE_OPTS = '--no-daemon'
    // evita colocar tokens diretamente aqui
  }

  stages {
    stage('Checkout') {
      steps {
        echo "Checkout do código"
        checkout scm
      }
    }

    stage('Prepare agent') {
      steps {
        script {
          // Torna o script executável caso exista
          if (fileExists('.jenkins/scripts/install_chrome.sh')) {
            sh 'chmod +x .jenkins/scripts/install_chrome.sh || true'
            // tente instalar o Chrome se necessário (script seguro: ignora falhas)
            sh ".jenkins/scripts/install_chrome.sh ${params.CHROME_VERSION} || true"
          } else {
            echo '.jenkins/scripts/install_chrome.sh não encontrado - pule instalação automática do Chrome.'
          }
        }
      }
    }

    stage('Build & Test') {
      steps {
        script {
          // Retentar testes uma vez em caso de falha transitória
          retry(2) {
            // Monta o comando gradle com propriedades passadas
            def gradleCmd = "./gradlew ${GRADLE_OPTS} clean test -Dselenide.headless=${params.HEADLESS} -Dselenide.baseUrl=\"${params.BASE_URL}\" -Dselenide.browser=${params.BROWSER} -Dselenide.autoOpen=${params.SELENIDE_AUTOOPEN}"
            echo "Executando: ${gradleCmd}"
            // Executa o build (saída colorizada por ansiColor acima)
            sh gradleCmd
          }
        }
      }
    }

    stage('Optional: Publish Allure') {
      when {
        expression { return params.PUBLISH_ALLURE }
      }
      steps {
        script {
          // Tentativa de publicação via plugin Allure — se o plugin não estiver instalado, apenas emitimos instrução
          try {
            allure results: [[path: 'build/allure-results']]
          } catch (err) {
            echo 'Falha ao publicar Allure via plugin (pode não estar instalado). Os resultados foram arquivados como artifacts.'
            echo err.toString()
          }
        }
      }
    }
  }

  post {
    always {
      script {
        // Publica resultados JUnit para o Jenkins (obrigatório)
        junit '**/build/test-results/test/*.xml'

        // Arquiva artifacts úteis para debugging
        archiveArtifacts artifacts: '**/build/reports/tests/**, **/build/allure-results/**, **/logs/**, **/screenshots/**', fingerprint: true, allowEmptyArchive: true

        // Expor logs adicionais se existirem
        if (fileExists('build/reports/tests')) {
          echo 'Relatórios de teste arquivados.'
        }

        // Limpeza do workspace é opcional; descomente se quiser sempre limpar
        // cleanWs()
      }
    }

    success {
      echo "Build e testes concluídos com sucesso"
    }

    failure {
      echo "Build ou testes falharam. Verificar JUnit e artifacts para diagnóstico."
    }
  }
}

