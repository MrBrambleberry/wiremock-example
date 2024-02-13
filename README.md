# Wiremock example

This is a Spring project that uses Maven as a build tool. Some options for aquiring these prequisites include:
- Windows: [OpenJDK](https://community.chocolatey.org/packages/openjdk) & [Maven](https://community.chocolatey.org/packages/maven) served via [Chocolatey](https://community.chocolatey.org/)
- Linux: [OpenJDK](https://formulae.brew.sh/formula/openjdk) & [Maven](https://formulae.brew.sh/formula/maven) served via [Homebrew](https://brew.sh/)


### Install maven dependencies
```
mvn clean install
```

### Run tests
```
mvn test
```

### Run project
```
mvn spring-boot:run
```
- The service will run on port 8080 by default unless it's already in use
- In that case it will select the next adjacent free port

### Get character data by name
```
curl -d "name=Luke Skywalker" -X POST http://localhost:8080/character
```
- If you don't wish to use curl any http client will work eg. [Postman](https://www.postman.com/) which supports importing curl statements to build requests
