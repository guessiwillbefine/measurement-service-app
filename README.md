# Measurement-service-app (backend)
# see also [frontend](https://github.com/guessiwillbefine/measurement-service-frontend) and [hardware](https://github.com/DeepPurpleTest/measurement-service-esp) part of this project
## authors
Vadym : [github](https://github.com/guessiwillbefine) | [linkedin](https://www.linkedin.com/in/vadym-storozhuk-407492248/) <br/>
Maksym : [github](https://github.com/DeepPurpleTest) | [linkedin](https://www.linkedin.com/in/maksim-viskovatov-93636b254/)

### about
This project is an example of a sensor monitoring system using web technologies and interface. Current stack of technologies we used:
- Java 17
- Spring boot 3.0.1
  - Hibernate
  - Spring Data JPA
  - Spring Data MongoDB
  - Security (JWT)
  - WebSockets
- RabbitMQ
- SQL : MySQL, H2 (with liquibase as migration tool)
- NoSQL : MongoDB
- Docker
- Swagger for docs

### Run
to run the project from docker :
```cmd
docker-compose up
```
