

# Docker Build Stage
FROM openjdk:17-jdk-alpine

COPY target/*.jar app.jar

ENV PORT 9992
EXPOSE $PORT

ENTRYPOINT ["java","-jar","-Xmx1024M","-Dserver.port=${PORT}","app.jar"]
