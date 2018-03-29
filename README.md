# update-pom-version

# build jar file
```shell
mvn clean package
```

# run jar file (outside project directory)
```shell
java -jar -Dpom.version=<new_version> -Dpom.path=<path_to_project> dist/update-pom-version*.jar 
```


# run jar file (inside project directory)
```shell
java -jar -Dpom.version=<new_version> dist/update-pom-version*.jar 
```
