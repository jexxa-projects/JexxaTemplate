[![Maven Test Build](https://github.com/jexxa-projects/JexxaTemplate/actions/workflows/mavenBuild.yml/badge.svg)](https://github.com/jexxa-projects/JexxaTemplate/actions/workflows/mavenBuild.yml)
[![New Release](https://github.com/jexxa-projects/JexxaTemplate/actions/workflows/newRelease.yml/badge.svg)](https://github.com/jexxa-projects/JexxaTemplate/actions/workflows/newRelease.yml)

# JexxaTemplate
This template can be used to start your own Jexxa application 

## Requirements

*   Java17 installed
*   Writing Java code and build your programs using maven
*   (Optional): Docker configured in swarm mode if you want to run the results in a docker environment. See [here](README-CICD.md) for more information.   

## Features

*   Simple maven project to start your first Jexxa-project, build it as self-contained jar and/or docker image.

*   Predefined package structure for a ports-and-adapter architecture in the context of domain driven design.

*   A template for unit tests to validate your domain logic. [This template can be used for your own tests](src/test/java/io/jexxa/jexxatemplate/applicationservice/BookStoreServiceTest.java).

*   A template for integration tests to run your tests against the running application. [This template can be used for your own tests](src/test/java/io/jexxa/jexxatemplate/integration/applicationservice/JexxaTemplateIT.java).

*   Predefined architectural tests that can be used without any further extensions: 
    *   [PatternLanguageTest](src/test/java/io/jexxa/jexxatemplate/architecture/PatternLanguageTest.java) validates the correct annotation of your application using project [Addend](http://addend.jexxa.io/). 
    *   [OnionArchitectureTest](src/test/java/io/jexxa/jexxatemplate/architecture/OnionArchitectureTest.java) validates dependencies between packages of your application.
    *   [StatelessApplicationCoreTest](src/test/java/io/jexxa/jexxatemplate/architecture/StatelessApplicationCoreTest.java) validates that your application core is stateless (except of your aggregates) and does not expose aggregates.

*   Predefined CI/CD scripts for GitHub including automatic dependency updates. 
 
## Create Project

*   In GitHub press `Use this template` 

*   Enter a `project name` for the repository. This template uses following convention

*   Project name of the repository should be equal to the name of the java application defined in maven 
    *   Name of the application should be equal the name of java class providing the main method
    *   Project name should be written in camel case notation, such as `JexxaTemplate`

*   After creating a new project, the GitHub-Action `Maven-Test Build' should successfully run 

## Adjust Project

*   Checkout the new project in your favorite IDE

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

## Adjust Release Version

```shell
mvn versions:set -DnewVersion='0.1.0-SNAPSHOT'
```

## Build and run the Project

### Maven (with integration tests)
To build the template with integration tests you need to run a postgres database and JMS message broker.
If you have docker running, you can use the [developer-stack.yml](deploy/developerStack.yml) to set up these infrastructure services. Then, you can enter the following command to build the project with integration tests und run the application:

```shell
mvn clean install
```
Note: If you search / replaced this file, you should see now `./target/<projectname>-jar-with-dependencies.jar`

### Maven (without integration tests)
If you do not have a running database and messaging bus running locally, you can enter the following command to build and run the application:
Note: In this case, not integration tests are running.


```shell
mvn clean install -P '!integrationTests'


java -jar "-Dio.jexxa.config.import=src/test/resources/jexxa-local.properties"  target/jexxatemplate-jar-with-dependencies.jar
```

### Properties files

Passwords and credentials are a crucial part of any production environment which must not be stored in a repository.
Jexxa itself addresses this issue by using two different properties files.

*   [`jexxa-application.properties`](src/main/resources/jexxa-application.properties): By default, this is the properties file used in production. Therefore, it does not
  include any secrets. Instead, you define a path to a secret file. The clustering environment then mounts these secrets
  into your containers in a secure way.

*   [`jexxa-test.properties`](src/test/resources/jexxa-test.properties):
    *   This file can be used by developers to define differences between development and production environment. For example, it can include credentials that are only used on the developer machine itself and can be stored in a repository.
    *   Since Jexxa loads the `jexxa-application.properties` by default, you just need to define the differences.
    *   This properties-file is automatically loaded if you use `JexxaTest` for your tests.

*   [`jexxa-local.properties`](src/test/resources/jexxa-local.properties):
    *   This file can be used in case you would not like to use any infrastructure service such as a DB. 
    *   Since Jexxa loads the `jexxa-application.properties` by default, you just need to define the differences.

### Run the entire environment 

To see how to Run the entire application environment in a docker-swarm environment follow the [README-DOCKER.md](README-CICD.md).
