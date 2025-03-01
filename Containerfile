FROM openjdk:17-jdk-alpine
ENV PROFILE="development"
EXPOSE 8081
COPY target/token.checker-1.00.00.jar token-checker.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "/token-checker.jar"]
