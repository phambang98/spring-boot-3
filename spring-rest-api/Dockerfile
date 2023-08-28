

# Docker Build Stage
FROM openjdk:17-jdk-alpine

COPY target/spring-rest-api.jar spring-rest-api.jar

ENV PORT 9992
EXPOSE $PORT

ENTRYPOINT ["java","-jar","-Xmx1024M","-Dserver.port=${PORT}","spring-rest-api.jar"]
