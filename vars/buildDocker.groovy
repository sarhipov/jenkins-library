def call(Map params = [:]) {
    stage('Build Docker Image') {
        steps {
            script {
                try {
                    docker.withRegistry('', params.dockerRegistryCredentialsId) {
                        def dockerImage = docker.build(params.dockerImageName, "-f Dockerfile .")
                        dockerImage.push()
                    }
                } catch (Exception e) {
                    echo "Error in Build Docker Image stage: ${e}"
                    currentBuild.result = 'FAILURE'
                }
            }
        }
    }
}