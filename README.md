# JexxaTemplate
Template to start your own Jexxa application 

## Requirements
* Java17 installed
* Writing Java code and build your programs using maven


### Maven:

In case you use this repository as template, please adjust the following sections in [pom.xml](pom.xml):
- `TODO (REQUIRED): Change your project name, groupId, artifactId, ... here.`
- `TODO (REQUIRED): Change the scm section to include correct repository.`

#### Build project
```shell
mvn clean install

java -jar -Dio.jexxa.config.import=/jexxa-developer.properties target/jexxatemplate-jar-with-dependencies.jar 
```

#### Create new Release
```shell
mvn release:prepare release:perform
```

#### Create local docker image
```shell
mvn -PlocalDockerImage jib:dockerBuild
```


## GitHub Actions 

- [mavenBuild.yml](.github/workflows/mavenBuild.yml): 
  - Builds the project after each push
  - Can be started manually from GitHub web page if required 
  
- [newRelease.yml](.github/workflows/newRelease.yml):
  - Manually create a new release using maven via GitHub web page
  - Can only be run via GitHub web page
  
- [publishImage.yml](.github/workflows/publishImage.yml)
  - Creates a new docker image as soon as a tag is created 
  - Is started automatically and can not be started manually
    
## Docker-Stacks 

- [developerStack.yml](deploy/developerStack.yml)
  - Includes all required dependencies to run the application during development on your local machine

- [jexxaTemplateStack.yml](deploy/docker-compose.yml)
  - Stack to run the application as stack in your production environment 



## Configure Secrets 

```shell
echo 'admin' | docker secret create jdbcUser -
echo 'admin' | docker secret create jdbcPassword -
echo 'artemis' | docker secret create jndiUser -
echo 'password' | docker secret create jndiPassword -
```

docker stack deploy --compose-file ./deploy/docker-compose.yml jexxatemplate
