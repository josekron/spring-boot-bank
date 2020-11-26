FROM openjdk:13-ea-16-jdk-alpine3.9

RUN mkdir -p /usr/share/springbootbank

COPY build/libs/springbootbank-1.0-SNAPSHOT.jar /usr/share/springbootbank/spring-boot-bank.jar

WORKDIR /usr/share/springbootbank

EXPOSE 8080

CMD ["java", "-jar", "spring-boot-bank.jar"]