# JexxaTemplate
Template to start your own Jexxa application 

## Requirements
* Java17 installed
* Writing Java code and build your programs using maven.


### Maven:

#### Build project
```shell
mvn clean install

java -jar -Dio.jexxa.config.import=/jexxatemplate-developer.properties target/jexxatemplate-jar-with-dependencies.jar 
```

#### Erstellen einer neuen Release:
```shell
mvn release:prepare release:perform
```

#### Lokales Docker image:
```shell
mvn -PlocalDockerImage jib:dockerBuild
```
