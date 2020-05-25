## Task:

Containerize a Java microservice web API application.

## Success Criteria:

Successful access to application rest end points running in a docker container from the host node browser.

## Steps:

1. Setup mysql docker container

```
docker search mysql

docker pull mysql

docker image ls mysql

docker run -p 3306:3306 --name labuser-mysql -e MYSQL_ROOT_PASSWORD=password -d mysql

docker inspect labuser-mysql | grep IPAddress
```
Note IPAddress value: (172.X.X.X)


2. Inspect mysql container:

```
docker exec -it labuser-mysql bash
<@container-shell> mysql -p
(Password= password)
<@mysql-shell> SHOW DATABASES;
<@mysql-shell> exit
<@container-shell> exit
```

3. Change "application.properties" entries in microservice app to point to mySQL. Comment out H2 entries and enable mysql ones.

```
spring.datasource.url=jdbc:mysql://{mysql-container-IPAddress}:3306/telemetrydb?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
```

4.  Build application:

```mvn clean package```

5. Using a text editor, create "Dockerfile" file in project root folder with following entries:

```
FROM adoptopenjdk/openjdk11-openj9:latest
COPY ./target/telemetry-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "telemetry-0.0.1-SNAPSHOT.jar"]
```


5. Build the container image: 

```docker build -t labuser/telemetry .```

6. View image:

```docker image ls```

7. Run container - with port-forward:

```docker run -p 3333:8080 --name labuser-telemetry labuser/telemetry```

8. View running container (in a different shell):

```docker ps -l```

9. Access web app in browser:

- http://127.0.0.1:3333/telemetry/api/v1/swagger-ui.html	(API docs and test client)
- http://127.0.0.1:3333/telemetry/api/v1/locations 		(simple json RPC response)
- http://127.0.0.1:3333/telemetry/api/v1/hal/locations 		(hal+json response)
- http://127.0.0.1:3333/telemetry/api/v1			(HAL Browser)


8. Stop containers:
```docker stop labuser-mysql```
```docker stop labuser-telemetry```
 

10. Delete docker images:
```docker image rm -f mysql:latest```
```docker image rm -f labuser/telemetry:latest```

11. Done!
