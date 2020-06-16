## Task:

Deploy and run a Java microservice Web API backed by in-memory database}

## Success Criteria:

Successful display application service end points in the browser.

## Steps:


1. Build & run application:

```mvn spring-boot:run```

2. Access web application using a browser on the same node:

- http://127.0.0.1:8080/telemetry/api/v1/swagger-ui.html	(API docs and test client)
- http://127.0.0.1:8080/telemetry/api/v1/locations 		(simple json RPC response)
- http://127.0.0.1:8080/telemetry/api/v1/hal/locations 		(hal+json response)
- http://127.0.0.1:8080/telemetry/api/v1/h2-console		(H2 database) [Username:sa, Password: ]
- http://127.0.0.1:8080/telemetry/api/v1			(HAL Browser)

3. Done!
