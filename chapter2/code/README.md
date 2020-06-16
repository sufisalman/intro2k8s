## Task:

Containerize a Java microservice web API application.

## Success Criteria:

Successful access to application rest end points running in a docker container from the host node browser.

## Steps:


1.  Build application:

```mvn clean package```

2. Using a text editor, create "Dockerfile" file in project root folder with following entries:

```
FROM adoptopenjdk/openjdk11-openj9:latest
COPY ./target/telemetry-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "telemetry-0.0.1-SNAPSHOT.jar"]
```


3. Build the container image: 

```docker build -t labuser/telemetry .```

4. View image:

```docker image ls```

5. Run container - with port-forward:

```docker run -p 3333:8080 labuser/telemetry```

6. View running container (in a different shell):

```docker ps -l```

7. Access web app in browser:

- http://127.0.0.1:3333/telemetry/api/v1/swagger-ui.html	(API docs and test client)
- http://127.0.0.1:3333/telemetry/api/v1/locations 		(simple json RPC response)
- http://127.0.0.1:3333/telemetry/api/v1/hal/locations 		(hal+json response)
- http://127.0.0.1:3333/telemetry/api/v1/h2-console		(H2 database) [JDBC URL: jdbc:h2:mem:telemetrydb, Username: sa, Password:  ]
- http://127.0.0.1:3333/telemetry/api/v1			(HAL Browser)

8. Stop container:
```docker stop <container-id>```
 

10. Delete docker image:
```docker image rm -f <image-id>```

11. Done!
