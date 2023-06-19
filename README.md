# Measurement-service-app (backend)
### see also [frontend](https://github.com/guessiwillbefine/measurement-service-frontend) and [hardware](https://github.com/DeepPurpleTest/measurement-service-esp) part of this project
## Authors
Vadym : [github](https://github.com/guessiwillbefine) | [linkedin](https://www.linkedin.com/in/vadym-storozhuk-407492248/) <br/>
Maksym : [github](https://github.com/DeepPurpleTest) | [linkedin](https://www.linkedin.com/in/maksim-viskovatov-93636b254/)

## About
This project is an example of a sensor monitoring system using web technologies and interface. 
Сonsists of three modules. 
<br>The first is the main module (ms-api), which implements methods for authentication, saving/updating entities and saving the results of sensor measurements. 
<br>ms-consumer - module, which is a subscriber of the MQ, sends messages via e-mail to user when measurements are higher than normal.
<br>journal-service - module of work with mongoDB, saves in certain events, such as user authentication, events related to sending e-mails.
Current stack of technologies we used:
- Java 17
- Spring boot 3.0.1
  - Spring Web
  - Hibernate
  - Spring Data JPA
  - Spring Data MongoDB
  - Security (JWT authentication)
  - WebSockets
- RabbitMQ
- SQL : MySQL, H2 (with liquibase as migration tool)
- NoSQL : MongoDB
- Docker
- Swagger for documentation

### Run
to run the project from docker :
```cmd
docker-compose up
```
