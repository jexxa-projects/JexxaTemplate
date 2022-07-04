[![Maven Test Build](https://github.com/jexxa-projects/JexxaTemplate/actions/workflows/mavenBuild.yml/badge.svg)](https://github.com/jexxa-projects/JexxaTemplate/actions/workflows/mavenBuild.yml)
[![New Release](https://github.com/jexxa-projects/JexxaTemplate/actions/workflows/newRelease.yml/badge.svg)](https://github.com/jexxa-projects/JexxaTemplate/actions/workflows/newRelease.yml)

# JexxaTemplate
This template can be used to start your own Jexxa application 

## Requirements

*   Java 17+ installed
*   IDE with maven support 
*   [Optional] Docker or Kubernetes if you want to run your application in a container. See [here](README-CICD.md) for more information.   
*   [Optional] A running [developer stack](deploy/developerStack.yml) providing a Postgres database, ActiveMQ broker, and Swagger-UI 

## Features

*   Build your first Jexxa-project as self-contained jar and/or docker image
    
*   Template for [Unit-](src/test/java/io/jexxa/jexxatemplate/applicationservice/BookStoreServiceTest.java) and [Integration tests](src/test/java/io/jexxa/jexxatemplate/integration/applicationservice/JexxaTemplateIT.java)

*   Predefined architectural tests for: 
    *   [Pattern Language](src/test/java/io/jexxa/jexxatemplate/architecture/PatternLanguageTest.java) to validate the correct annotation of your application using project [Addend](http://addend.jexxa.io/) 
    *   [Onion Architecture](src/test/java/io/jexxa/jexxatemplate/architecture/OnionArchitectureTest.java) to validates dependencies between packages of your application
    *   [Usage of Aggregates](src/test/java/io/jexxa/jexxatemplate/architecture/StatelessApplicationCoreTest.java) to validate that your business logic is not exposed

*   Predefined CI/CD scripts for GitHub including automatic dependency updates 
 
## Create new Project from Template

*   In GitHub press `Use this template` (requires GitHub account) or fork the project  

*   Enter a `project name` for the repository. This template uses following convention:
    *   Project name should be written in camel case notation, such as `JexxaTemplate`
    *   Project name of the repository is equal to the name of the java application

## Build the Project

*   Checkout the new project in your favorite IDE

*   Without running Developer Stack:
    ```shell
    mvn clean install -P '!integrationTests'

    java -jar "-Dio.jexxa.config.import=src/test/resources/jexxa-local.properties" \
              target/jexxatemplate-jar-with-dependencies.jar
    ```

*   [Optional] **With** running Developer Stack:
    ```shell
    mvn clean install
    
    java -jar "-Dio.jexxa.config.import=src/test/resources/jexxa-test.properties" \ 
              target/jexxatemplate-jar-with-dependencies.jar
    ```

*   See [here](https://github.com/jexxa-projects/JexxaTutorials/blob/main/BookStore/README.md#execute-some-commands-using-curl) how to use the application from command line with `curl`

*   [Optional] See [here](https://github.com/jexxa-projects/JexxaTutorials/blob/main/BookStore/README-OPENAPI.md#explore-openapi) how to use the application with Swagger-UI

## Start Developing your Project

### Adjust Project 
*   Refactor/Rename file `JexxaTemplate.java` into `<ProjektName>.java` within your IDE

*   Refactor/Rename the GroupId (directory) `io.jexxa.jexxatemplate` into `com.github.<your-github-account>` for example within your IDE

*   Adjust all sections marked with TODO (and remove TODO statement) in : 
    *    [pom.xml](pom.xml) 
    *    [jexxa-application.properties](src/main/resources/jexxa-application.properties) 
    *    [jexxa-test.properties](src/test/resources/jexxa-test.properties)
    *    [docker-compose.yml](deploy/docker-compose.yml)

*   In README.md:
    *   Search/replace (case-sensitive) `JexxaTemplate` by `<ProjectName>`
    *   Search/replace (case-sensitive) `jexxatemplate` by `<projectname>`
    *   Adjust the badges (first two lines)

*   Adjust release version
    ```shell
    mvn versions:set -DnewVersion='0.1.0-SNAPSHOT'
    ```

### Set up the CI/CD Pipeline  

To see how to run your application together with a CI/CD pipeline see [here](README-CICD.md).
