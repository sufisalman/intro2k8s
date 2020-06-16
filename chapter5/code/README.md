## Task:

Configure a K8s cluster for dev, stage & prod environments using kustomize. 

## Success Criteria:

All three environments are successfully up and running in the K8s cluster in separate namespaces.

## Steps:

1. Install kustomize - if not already installed:
```
curl -s "https://raw.githubusercontent.com/\
kubernetes-sigs/kustomize/master/hack/install_kustomize.sh"  
```

2. View structure of 'code' directory:
```
tree telemetry-kustomized
```

3. Build and view generated manifests for all environments:
```
kustomize build ./telemetry-kustomized/overlay/dev > testconfig.yaml && nano testconfig.yaml
kustomize build ./telemetry-kustomized/overlay/staging > testconfig.yaml && nano testconfig.yaml
kustomize build ./telemetry-kustomized/overlay/production > testconfig.yaml && nano testconfig.yaml      
```

4. Dry run kustomize build for all 3 environments:
```
kustomize build ./telemetry-kustomized/overlay/dev | kubectl apply --dry-run -f -
kustomize build ./telemetry-kustomized/overlay/staging | kubectl apply --dry-run -f -
kustomize build ./telemetry-kustomized/overlay/production | kubectl apply --dry-run -f -
```

5. Start minikube cluster - if not already running:
```
sudo minikube start
```

6. Apply manifests to the cluster for all environments:
```
kustomize build ./telemetry-kustomized/overlay/dev | kubectl apply -f -
kustomize build ./telemetry-kustomized/overlay/staging | kubectl apply -f -
kustomize build ./telemetry-kustomized/overlay/production | kubectl apply -f -
```

7. Fireup K8s dashboard and confirm that all 3 environments are up and running (check namespaces):
```
sudo minikube dashboard
```

8. Access dev, staging and production versions of the solution:

dev:
```
http://127.0.0.1:30030/telemetry/api/v1/swagger-ui.html
```

staging:
```
http://127.0.0.1:30031/telemetry/api/v1/swagger-ui.html
```

production:
```
http://127.0.0.1:30032/telemetry/api/v1/swagger-ui.html
```


9. Stop minikube cluster:
```
sudo minikube stop
```

10. Done! :-)


