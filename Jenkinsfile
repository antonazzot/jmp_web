pipeline {
    agent any
    tools{
        maven 'maven_3_8_6'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/master'], [name: '*/05-ci-cd']], extensions: [], userRemoteConfigs: [[credentialsId: 'e0850795-67c3-4691-940c-033ab359133c', url: 'https://github.com/antonazzot/jmp_web']]])
                sh 'mvn clean install'
            }
        }
        stage('Build jmp image '){
            steps{
                script{
                    sh 'docker buildx build . --builder "$(docker buildx create --driver-opt env.BUILDKIT_STEP_LOG_MAX_SIZE=10000000 --driver-opt env.BUILDKIT_STEP_LOG_MAX_SPEED=10000000)" -t epamantontsyrkunou/jmp-1.0.0 '
                }
            }
        }
        stage('Build db image'){
            steps{
                script{
                    sh 'docker build ./delivery/db -t epamantontsyrkunou/jmpdb'
                }
            }
        }
        stage('Push image to Hub'){
            steps{
                script{
                    withCredentials([string(credentialsId: 'hub', variable: 'hubuser')]) {
                   sh 'docker login -u epamantontsyrkunou -p ${hubuser}'

}
                   sh 'docker push epamantontsyrkunou/jmp-1.0.0'
                }
            }
        }
        stage('Push db image to Hub'){
            steps{
                script{
                   sh 'docker push epamantontsyrkunou/jmpdb'
                }
            }
        }
        stage('Deploy to k8s'){
            steps{
                script{
                   sh 'helm upgrade jmpapp-1.3.0.0 --set url=jdbc:postgresql://10.105.76.53:5432/jmpdb ./delivery/mainchart'
                }
            }
        }
    }
}
