

FROM openjdk:17

EXPOSE  8988

ADD target/spring-boot-3.jar spring-boot-3.jar

ENTRYPOINT ["java","-jar","/spring-boot-3.jar"]