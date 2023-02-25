FROM maven:latest as mvn
WORKDIR /app
COPY . .
RUN mvn package
RUN mvn org.springframework.boot:spring-boot-maven-plugin:run -pl ms-api