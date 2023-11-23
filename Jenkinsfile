@Library('shared-library') _

buildDocker(
        agentLabel: 'fusion-linux',
        dockerRegistryCredentialsId: 'dockerhub',
        dockerImageName: 'sarhipov/hello-world:latest'
)

k8sDeploy(
        kubernetesDeploymentName: 'hello-world',
        dockerImageName: 'sarhipov/hello-world:latest',
        kubeconfigCredentialsId: 'dev-kubeconfig'
)