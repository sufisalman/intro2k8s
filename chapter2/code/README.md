Task: {Deploy a microservice Web API with mySQL backend}
Success Criteria: {Successful display of rest end points in Swagger UI} 

- Build & Run: ```mvn spring-boot:run```
- Open API Access: http://127.0.0.1:8080/telemetry/api/v1/swagger-ui.html
- H2-Database Access:  http://127.0.0.1:8080/telemetry/api/v1/h2-console
- Sample URI for simple JSON media: http://127.0.0.1:8080/telemetry/api/v1/locations
- Sample URI for HAL+JSON media: http://127.0.0.1:8080/telemetry/api/v1/hal/locations
