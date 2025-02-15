### README: Microservicios con Spring Boot 
---
## **Descripción de los Servicios**

- Servicio de Productos (Product Service)  
- Servicio de Pedidos (Order Service)  
- Servicio de Inventario (Inventory Service)  
- Servicio de Notificaciones (Notification Service)  
- API Gateway con **Spring Cloud Gateway MVC**  
- Frontend de la tienda con **Angular 18**  

---

## **Tech Stack**

Este proyecto utiliza las siguientes tecnologías:

- **Spring Boot**  
- **Angular**  
- **MongoDB**  
- **MySQL**  
- **Kafka**  
- **Keycloak**  
- **Test Containers** con **Wiremock**  
- **Grafana Stack:** *Prometheus, Grafana, Loki y Tempo*  
- **API Gateway:** *Spring Cloud Gateway MVC*  
- **Kubernetes**  

---

## **Arquitectura de la Aplicación**
![Arquitectura](https://github.com/user-attachments/assets/d4ef38bd-8ae5-4cc7-9ac5-7a8e5ec3c969)

---

## 🖥️ **Cómo ejecutar la aplicación Frontend**

Asegúrate de tener instalados los siguientes requisitos:

- **Node.js**  
- **NPM**  
- **Angular CLI**  

Ejecuta los siguientes comandos para iniciar la aplicación frontend:

```shell
cd frontend
npm install
npm run start
```

---

## 🛠️ **Cómo construir los servicios Backend**

Ejecuta el siguiente comando para compilar y empaquetar los servicios backend en un contenedor Docker:

```shell
mvn spring-boot:build-image -DdockerPassword=<tu-contraseña-docker>
```

Este comando compilará y empaquetará los servicios en un contenedor Docker y lo subirá a tu cuenta de Docker Hub.

---

## 🚀 **Cómo ejecutar los servicios Backend**

Asegúrate de tener instalados los siguientes requisitos:

- **Java 21**  
- **Docker**  
- **Kind Cluster:** [Guía de instalación](https://kind.sigs.k8s.io/docs/user/quick-start/#installation)  

---

### 🟢 **1. Iniciar el Clúster Kind**

Ejecuta el script para crear el clúster de Kubernetes con Kind:

```shell
./k8s/kind/create-kind-cluster.sh
```

Este script creará un clúster *Kind* y precargará todas las imágenes Docker necesarias en el clúster. Esto ahorrará tiempo al desplegar la aplicación.

---

### 🛡️ **2. Desplegar la Infraestructura**

Aplica el archivo `infrastructure.yaml` para desplegar los recursos de infraestructura:

```shell
kubectl apply -f k8s/manifests/infrastructure.yaml
```

---

### ⚙️ **3. Desplegar los Servicios**

Aplica el archivo `applications.yaml` para desplegar los servicios de la aplicación:

```shell
kubectl apply -f k8s/manifests/applications.yaml
```

---

## 🌐 **Cómo acceder a los Servicios**

### 📡 **1. Acceder al API Gateway**

Haz un *port-forward* del servicio del Gateway a tu máquina local:

```shell
kubectl port-forward svc/gateway-service 9000:9000
```

---

### 🔐 **2. Acceder a la Consola de Administración de Keycloak**

Haz un *port-forward* del servicio de Keycloak:

```shell
kubectl port-forward svc/keycloak 8080:8080
```

---

### 📊 **3. Acceder a los Dashboards de Grafana**

Haz un *port-forward* del servicio de Grafana:

```shell
kubectl port-forward svc/grafana 3000:3000
```

---

✅ ¡Listo! Ahora puedes explorar los servicios, gestionar accesos y visualizar métricas del sistema. 🚀✨
