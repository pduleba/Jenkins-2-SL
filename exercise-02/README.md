# Jenkins-2-SL - Exercise 02 - Pipeline 

### Prerequisites
[app-rest-api](https://github.com/pduleba/Docker-SL/tree/master/exercise-03-rest-api-crud/app-rest-api) project as
* standard build using
* * maven 3.5.x
* * jdk 1.8.x
* * post build actions
* * * Archive in Artifactory
* * * E-mail notification
* * * Publish JUnit test results reports
* SMTP Server
* [Snippet generator](http://localhost:8080/job/rest-api-pipeline/pipeline-syntax/) for Groovy

### Installation
* configure maven version `maven-3.6.1` under Jenkins Management -> Global Tools
* create job `Pipeline` type using `rest-api-pipeline.groovy` script