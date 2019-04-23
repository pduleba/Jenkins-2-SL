# Jenkins-2-SL - Exercise 05 - MultiBranch pipeline (with Jenkins BlueOcean)

### Before usage

#### Introduction
This is result of [build a multibranch pipeline project](https://jenkins.io/doc/tutorials/build-a-multibranch-pipeline-project/)
using [Docker-SL](https://github.com/pduleba/Docker-SL) repository.

#### Prerequisites
Local jenkins (i.e. docker instance) needs:
* * define JDK `1.8.0_201` needs to be defined in Manage Jenkins -> Global tools -> JDK
* * define MAVEN `3.6.0` needs to be defined in Manage Jenkins -> Global tools -> MAVEN
* Start MySQL DB via Docker container using:
* * `app-db/exec-run-network.bat`
* * `app-db/exec-build.bat`
* * `app-db/exec-run.bat`


### Usage
* `run-jenkins.bat` - to run BlueOcean Docker container
* `run-jenkins-connect.bat` - to join running BlueOcean Docker container
