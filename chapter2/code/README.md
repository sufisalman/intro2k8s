## Task: {Containerize a Java Microservice Web API}
## Success Criteria: {Successful access rest end points running in a docker container from the host node brwoser } 

1.  Build application:

```mvn clean package```

2. Create "Dockerfile" file in project root folder with following entries:

```FROM adoptopenjdk/openjdk11-openj9:latest```
```COPY ./target/telemetry-0.0.1-SNAPSHOT.jar /usr/app/```
```WORKDIR /usr/app```
```EXPOSE 8080```
```ENTRYPOINT ["java", "-jar", "telemetry-0.0.1-SNAPSHOT.jar"]```

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
- http://127.0.0.1:3333/telemetry/api/v1/h2-console		(H2 database) [Username:sa, Password: ]
- http://127.0.0.1:3333/telemetry/api/v1			(HAL Browser)

8. Done!

