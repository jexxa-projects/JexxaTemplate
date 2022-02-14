# JexxaTemplate
Template to start your own Jexxa application 

## Requirements
* Java17 installed
* Writing Java code and build your programs using maven


### Maven:

#### Build project
```shell
mvn clean install

java -jar -Dio.jexxa.config.import=/jexxatemplate-developer.properties target/jexxatemplate-jar-with-dependencies.jar 
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


