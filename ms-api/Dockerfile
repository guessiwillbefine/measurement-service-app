FROM maven:latest AS build
WORKDIR /app
COPY ./ /app
RUN mvn package -DskipTests -pl '!e2e-tests'

FROM openjdk:17-oracle
WORKDIR /app
COPY --from=build /app/ms-api/target/ms-api.jar .
CMD ["java", "-jar", "ms-api.jar"]
