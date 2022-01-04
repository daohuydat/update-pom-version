# update-pom-version

## build jar file
```shell
mvn clean package
```

## run jar file (outside project directory)
```shell
java -Dpom.version=<new_version> -Dpom.path=<path_to_project> -jar dist/update-pom-version*.jar 
```


## run jar file (inside project directory)
```shell
java -Dpom.version=<new_version> -jar update-pom-version*.jar 
```
