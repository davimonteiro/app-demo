# App Demo

This a Java application
built using [Spring Boot](https://spring.io/projects/spring-boot),
[Spring Data JPA](https://spring.io/projects/spring-data-jpa) and
[H2](https://www.h2database.com/html/main.html).

## Requirements
| [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) | [Gradle v7.6](https://gradle.org/) | [Git](https://git-scm.com/) |


### Running locally

### Step 1 - Checkout

```bash
git clone https://github.com/davimonteiro/app-demo
cd app-demo
```  

### Step 2 - Packaging

```bash
./gradlew build
``` 

### Step 3 - Running


```bash
java -jar build/libs/app-demo-0.0.1-SNAPSHOT.jar
```

```bash
./gradlew bootRun
```