/// k8sDeploy.groovy
def call(Map params = [:]) {
    pipeline {
        agent {
            label params.agentLabel ?: 'any'
        }
        stages {
            stage('Deploy to Kubernetes') {
                steps {
                    script {
                        try {
                            def k8sDeployment = readFile('deployment.yaml')
                            k8sDeployment = k8sDeployment.replaceAll('\\$KUBERNETES_DEPLOYMENT_NAME', params.kubernetesDeploymentName)
                            k8sDeployment = k8sDeployment.replaceAll('\\$DOCKER_IMAGE_NAME', params.dockerImageName)

                            withCredentials([file(credentialsId: params.kubeconfigCredentialsId, variable: 'KUBECONFIG')]) {
                                sh "KUBECONFIG=$KUBECONFIG echo '${k8sDeployment}' | kubectl apply -f -"
                            }
                        } catch (Exception e) {
                            echo "Error in Deploy to Kubernetes stage: ${e}"
                            currentBuild.result = 'FAILURE'
                        }
                    }
                }
            }
        }
    }
}