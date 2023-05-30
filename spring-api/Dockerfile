

FROM openjdk:17

EXPOSE  9990

COPY target/spring-api.jar spring-api.jar

WORKDIR target/spring-api.jar

ENTRYPOINT ["java","-jar","/spring-api.jar"]