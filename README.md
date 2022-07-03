[![Maven Test Build](https://github.com/jexxa-projects/JexxaTemplate/actions/workflows/mavenBuild.yml/badge.svg)](https://github.com/jexxa-projects/JexxaTemplate/actions/workflows/mavenBuild.yml)
[![New Release](https://github.com/jexxa-projects/JexxaTemplate/actions/workflows/newRelease.yml/badge.svg)](https://github.com/jexxa-projects/JexxaTemplate/actions/workflows/newRelease.yml)

# JexxaTemplate
This template can be used to start your own Jexxa application 

## Requirements

*   Java17 installed
*   IDE with maven support 
*   [Optional] Docker or Kubernetes if you want to run your application in a container. See [here](README-CICD.md) for more information.   
*   [Optional] A running [developer stack](deploy/developerStack.yml) providing a Postgres database and ActiveMQ broker

## Features

*   Build your first Jexxa-project as self-contained jar and/or docker image
    
*   [Template for unit tests](src/test/java/io/jexxa/jexxatemplate/applicationservice/BookStoreServiceTest.java)

*   [Template for integration tests](src/test/java/io/jexxa/jexxatemplate/integration/applicationservice/JexxaTemplateIT.java)

*   Predefined architectural tests for: 
    *   [Pattern Language](src/test/java/io/jexxa/jexxatemplate/architecture/PatternLanguageTest.java) to validate the correct annotation of your application using project [Addend](http://addend.jexxa.io/) 
    *   [Onion Architecture](src/test/java/io/jexxa/jexxatemplate/architecture/OnionArchitectureTest.java) to validates dependencies between packages of your application
    *   [Usage of Aggregates](src/test/java/io/jexxa/jexxatemplate/architecture/StatelessApplicationCoreTest.java) to validate that they are not exposed

*   Predefined CI/CD scripts for GitHub including automatic dependency updates 
 
## Create new Project from Template

*   In GitHub press `Use this template` 

*   Enter a `project name` for the repository. This template uses following convention

*   Project name of the repository should be equal to the name of the java application defined in maven 
    *   Name of the application should be equal the name of java class providing the main method
    *   Project name should be written in camel case notation, such as `JexxaTemplate`

*   After creating a new project, the GitHub-Action `Maven-Test Build' should successfully run 

## Build the Project

*   Checkout the new project in your favorite IDE

*   Without running Developer Stack:
    ```shell
    mvn clean install -P '!integrationTests'

    java -jar "-Dio.jexxa.config.import=src/test/resources/jexxa-local.properties"  target/jexxatemplate-jar-with-dependencies.jar
    ```

*   [Optional] **With** running Developer Stack:
    ```shell
    mvn clean install
    
    java -jar "-Dio.jexxa.config.import=src/test/resources/jexxa-test.properties"  target/jexxatemplate-jar-with-dependencies.jar
    ```


## Start Developing your own Project

### Adjust Project Name
*   Adjust all entries in [pom.xml](pom.xml) marked with `TODO (REQUIRED)`
    *   Optional: If you adjust GroupId `<groupId>io.jexxa.jexxatemplate</groupId>` please also refactor the directory `io.jexxa.jexxatemplate` within your IDE

*   Refactor/Rename file `JexxaTemplate.java` into `<ProjektName>.java` within your IDE

*   Adjust all TODOs in [docker-compose.yml](deploy/docker-compose.yml)

*   In README.md:
    *   search/replace (case-sensitive) `JexxaTemplate` by `<ProjectName>`
    *   search/replace (case-sensitive) `jexxatemplate` by `<projectname>`
    *   adjust the badges (first two lines)

*   In [jexxa-application.properties](src/main/resources/jexxa-application.properties) adjust all TODOs

*   In [jexxa-test.properties](src/test/resources/jexxa-test.properties) adjust all TODOs

### Adjust Release Version

```shell
mvn versions:set -DnewVersion='0.1.0-SNAPSHOT'
```


### Use the CI/CD Pipeline  

To see how to run your application together with a CI/CD pipeline see [here](README-CICD.md).
