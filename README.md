# zatec-backend-service

This backend service aims to integrate with  Chuck Norris API and the Star Wars API to;
1. Fetch All Jokes Categories
2. Fetch All Star Wars People
3. Search for jokes and People.

## Technologies
- **Java 17** - Language
- **Spring Boot 2.7.0** - Server side framework
- **Maven** - Build tool
- **Docker** - Containerizing framework
- **Swagger** - API documentation
- **JUnit,Hamcrest** - Unit testing
- **Cloud Platform** - Heroku
- **CI / CD** - Github Actions

## Demo Link
Frontend App: https://zatec-frontend-app.herokuapp.com/

Swagger API documentation:  https://zatec-backend-service.herokuapp.com/swagger-ui.html

## Running the server locally
Use the below command to build and run the application:
```  
mvn clean package
```  
then run
```  
mvn spring-boot:run
```
The application would start on port 8080

## Running the server in Docker Container
 Install Docker desktop and run this command to build image 

```  
docker  build -t zatec-backend-service .
```  
and run container with

```  
docker  container run -d  -p 8080:8080 zatec-backend-service
``` 

## Monitoring and Metrics
Actuator endpoint expose the following operation:

- **/metrics** Shows “metrics” information for the current application.

- **/health** Shows application health information.

- **/info** Displays arbitrary application info.

All the actuator endpoints is available via <http://localhost:8080/actuator>.

## License

MIT license @ [Ahmed Kabiru](https://www.linkedin.com/in/ahmedkabiru/)
