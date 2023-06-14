# Project Name

> NOTE: Spring Cloud Sleuth will not work with Spring Boot 3.x onward. The last major version of Spring Boot that Sleuth will support is 2.x. 
> The core of this project got moved to Micrometer Tracing project and the instrumentations will be moved to Micrometer and all respective projects (no longer all instrumentations will be done in a single repository.
> You can check the Micrometer Tracing migration guide to learn how to migrate from Spring Cloud Sleuth to Micrometer Tracing.

for more detail: https://github.com/spring-cloud/spring-cloud-sleuth/tree/3.1.x

## Getting Started
To run the code in local with Docker-Compose, please follow these steps:

### Prerequisites

Java 17
Maven
Docker
Docker-Compose

### Usage

1. Run the following command to start the containers in detached mode. This will start the application and its dependencies, such as databases or message queues:
    ```shell
    docker-compose up -d
    ```
2. Run the project on your IDE with 3 different port
   ```text
   server.port=8080;spring.zipkin.service.name=SERVICE_1;spring.profiles.active=sev0
   server.port=8090;spring.zipkin.service.name=SERVICE_2;spring.profiles.active=sev1
   server.port=8099;spring.zipkin.service.name=SERVICE_3;spring.profiles.active=sev2
   ```
3. You can verify that the application is running by navigating to http://localhost:8080/swagger-ui/, http://localhost:8099/swagger-ui/, http://localhost:8090/swagger-ui/ in your web browser.

