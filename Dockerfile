FROM maven:latest as mvn
WORKDIR /app
COPY . .
RUN mvn package
#ENV MYSQL_HOST=mysqldb
RUN mvn org.springframework.boot:spring-boot-maven-plugin:run -pl ms-api -DskipTests
#CMD ["mvn", "spring-boot:run"]