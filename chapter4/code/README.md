## Task:

Deploy microservice web API and MySQL database in a K8s cluster. 

## Success Criteria:

Successful response from Web API REST endpoints running in a K8s cluster.

## Steps:

1. Remove all running containers:

```
docker container rm -f $(docker container ls -aq)
```

2. Setup mysql docker container:

```
docker search mysql

docker pull mysql

docker image ls mysql

docker image tag mysql:latest labuser/mysql:latest

docker run -p 3306:3306 --name labuser-mysql -e MYSQL_ROOT_PASSWORD=password -d labuser/mysql

docker inspect labuser/mysql | grep IPAddress

```
@Notethe value for IPAddress (172.17.0.X)

3. Change "application.properties" entries for "DB_HOST" in microservice app to point to mySQL. Needed for error free app build.
 
```
spring.datasource.url=jdbc:mysql://${DB_HOST:172.17.0.X}:${DB_PORT:3306}/${DB_NAME:telemetrydb}?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
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

6. Build app container image: 

```docker build -t labuser/telemetry .```

7. View image:

```docker image ls```

8. Remove all running containers (We will let K8s create containers later!):

```
docker container rm -f $(docker container ls -aq)
```

9. Start minikube cluster
```
sudo minikube start
```

10. Deploy solution to cluster:
```
kubectl apply -f telemetry.yaml
```

11. Start K8s dashboard and view all configured resources in "telemetry-dev" namespace:
```
sudo minikube dashboard
```

12. View configured resources through cli:
```
kubectl get ns
kubectl get pods --namespace=telemetry-dev
kubectl get services --namespace=telemetry-dev
kubectl get pvc --namespace=telemetry-dev
kubectl get pv --namespace=telemetry-dev
....
....
```

13. Access app in browser:

- http://127.0.0.1:30030/telemetry/api/v1/swagger-ui.html	(API docs and test client)
- http://127.0.0.1:30030/telemetry/api/v1/locations 		(simple json RPC response)
- http://127.0.0.1:30030/telemetry/api/v1/hal/locations 	(hal+json response)
			

14. Delete K8s namespace (this should delete all resources within the namespace. PV is not bound to namespace.):
```
kubectl delete ns telemetry-dev
kubectl delete pv mysql-pv

```
 
15. Stop K8s cluster:
```
sudo minikube stop
```
16. Done!
