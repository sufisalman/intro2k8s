1- Create "Dockerfile" file in project root with following entries:
##########################
FROM adoptopenjdk/openjdk11-openj9:latest
COPY ./target/telemetry-0.0.1-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "telemetry-0.0.1-SNAPSHOT.jar"]
#########################

2- Build the container image: 
docker build -t labuser/telemetry .

3- View image:
docker image ls

4- Run container - with port-forward:

docker run -p 3333:8080 labuser/telemetry

5- View running container (in a differnet shell):
docker ps

6- Access web app in browser:

http://127.0.0.1:3333/api/v1
http://127.0.0.1:3333/api/v1/swagger-ui.html
http://127.0.0.1:3333/api/v1/hal/locations
http://127.0.0.1:3333/h2-console

7- Done!



