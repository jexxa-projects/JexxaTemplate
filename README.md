# JexxaTemplate
This template can be use to start your own Jexxa application 

## Requirements
* Java17 installed
* Writing Java code and build your programs using maven
* (Optional): Docker configured in swarm mode if you want to run the results in a docker environment  

## Create Project
- In GitHub pres `Use this template` 

- Enter a `project name` for the repository. This template uses following convention
  - Project name of the repository should be equal to the name of the java application defined in maven 
  - Name of the application should be equal the name of java class providing the main method 
  - Project name should be written in camel case notation, such as `JexxaTemplate`

- After creating a new project, the GitHub-Action `Maven-Test Build' should successfuly run 

## Adjust Project 
- Checkout the new project in your favorite IDE 

- Adjust all entries in [pom.xml](pom.xml) marked with `TODO (REQUIRED)`
  - Optional: If you adjust GroupId `<groupId>io.jexxa.jexxatemplate</groupId>` please also refactor the directory `io.jexxa.jexxatemplate` within your IDE

- Refactor/Rename file `JexxaTemplate.java` into `<ProjektName>.java` within your IDE
- Adjust all TODOs in [docker-compose.yml](deploy/docker-compose.yml)
- In README.md search/replace (case-sensitive) `JexxaTemplate` by `<ProjectName>`
- In README.md search/replace (case-sensitive) `jexxatemplate` by `<projectname>`
- In [jexxa-application.properties](src/main/resources/jexxa-application.properties) adjust all TODOs
- In [jexxa-developer.properties](src/main/resources/jexxa-developer.properties) adjust all TODOs


## Adjust Release Version

```shell
mvn versions:set -DnewVersion='0.1.0-SNAPSHOT'
```

## Build Porject
### Maven:
```shell
mvn clean install

java -jar "-Dio.jexxa.config.import=/jexxa-developer.properties" ./target/jexxatemplate-jar-with-dependencies.jar
```
Note: If you search / replaced this file, you should see now `./target/<projectname>-jar-with-dependencies.jar`


## Create new release and docker image
If you want to create and docker image just go to GitHub actions and start action `New Release`    

### GitHub Actions 

- [mavenBuild.yml](.github/workflows/mavenBuild.yml): 
  - Builds the project after each push
  - Can be started manually from GitHub web page if required 
  
- [newRelease.yml](.github/workflows/newRelease.yml):
  - Manually create a new release using maven via GitHub web page
  - Can only be run via GitHub web page
  
### Docker-Stacks 

- [developerStack.yml](deploy/developerStack.yml)
  - Includes all required dependencies to run the application during development on your local machine

- [docker-compose.yml](deploy/docker-compose.yml)
  - Stack to run the application as stack in your production environment 


### Configure Secrets 
In order to use docker-compose.yml you have to configure following secrets. 
Please note that the `echo` command states the content of the secret 

```shell
echo 'admin' | docker secret create jdbcUser -
echo 'admin' | docker secret create jdbcPassword -
echo 'artemis' | docker secret create jndiUser -
echo 'password' | docker secret create jndiPassword -
```
### Deploy Stack
In order to deploy the stack, you can use following command
```shell
docker stack deploy --compose-file ./deploy/docker-compose.yml jexxatemplate
```
