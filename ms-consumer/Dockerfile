FROM maven:latest AS build
WORKDIR /app
COPY ./ /app
RUN mvn package -DskipTests -pl '!e2e-tests'

FROM openjdk:17-oracle
WORKDIR /app
COPY --from=build /app/ms-consumer/target/ms-consumer.jar .
CMD ["java", "-jar", "ms-consumer.jar"]