# 🗂️ Task Management System – Backend (Local Setup)

This repository contains the **backend** of a Task Management System built with **Java 17**, **Spring Boot**, **Tomcat 10**, and **MySQL 8**.  
It runs locally via **Docker Compose**; IntelliJ IDEA is used for coding, DBeaver for DB inspection, and Postman for API testing.

---

## 🚀 Tech Stack
| Layer      | Tool / Version |
|------------|----------------|
| Language   | Java 17        |
| Framework  | Spring Boot    |
| Runtime    | Tomcat 10 (WAR)|
| Database   | MySQL 8        |
| Container  | Docker + Docker Compose |
| IDE        | IntelliJ IDEA  |
| DB Client  | DBeaver        |
| API Client | Postman        |

---

## 🏗️ Local Setup with Docker Compose

### 1. Clone the Repository
```bash
git clone https://github.com/Abhijithjshetty/Task-Management-System.git
cd Task-Management-System
```

### 2. Build the WAR
```bash
mvn clean package
```

### 3. Copy WAR into `webapps/`
```bash
mkdir -p webapps
cp target/*.war webapps/
```

### 4. Spin Up Containers
```bash
docker compose up -d
```

**Compose file (`docker-compose.yml`)**:
```yaml
version: '3'

services:
  task_management:
    image: tomcat:10.0
    container_name: task_management
    ports:
      - "8080:8080"
    volumes:
      - ./webapps:/usr/local/tomcat/webapps
    networks:
      task:
        ipv4_address: 192.168.100.2
    restart: always

  taskdb:
    image: mysql:8.0
    container_name: taskdb
    environment:
      MYSQL_ROOT_PASSWORD: task112233
      MYSQL_USER: task-admin
      MYSQL_PASSWORD: task112233
      MYSQL_DATABASE: task_management
    ports:
      - "3306:3306"
    networks:
      task:
        ipv4_address: 192.168.100.3
    restart: always

networks:
  task:
    driver: bridge
    ipam:
      driver: default
      config:
        - subnet: 192.168.100.0/24
```

> **Note:** Credentials are for local setup only. In production, use environment variables or secrets manager.

---

## 🧑‍💻 Development Workflow

| Step | Tool          | What to Do                                                         |
| ---- | ------------- | ------------------------------------------------------------------ |
| Code | IntelliJ IDEA | Open project → code → `mvn package`                                |
| DB   | DBeaver       | Connect to MySQL `localhost:3306` → DB `task_management`           |
| API  | Postman       | Import Postman collection → test endpoints                         |

---

## 📬 Postman Collection

👉 **Download or import Postman collection:**  
[📂 TaskManager.postman_collection.json](docs/TaskManager.postman_collection.json)

> Or use this public link if hosted on Postman:  
> [🌐 Open in Postman](https://www.postman.com/collections/your-public-link)

---

## 🔑 API Endpoints Overview

### 🔐 Authentication
| Endpoint                                         | Method | Description             |
|--------------------------------------------------|--------|-------------------------|
| `/task-core-api/public/sign_up`                 | POST   | Register user           |
| `/task-core-api/public/login`                   | POST   | Login user (get JWT)    |

### 👤 User
| Endpoint                                         | Method | Description             |
|--------------------------------------------------|--------|-------------------------|
| `/task-core-api/api/v1/user`                    | GET    | View logged-in profile  |

### ✅ Tasks
| Endpoint                                         | Method | Description                        |
|--------------------------------------------------|--------|------------------------------------|
| `/task-core-api/api/v1/task`                    | POST   | Create a new task (User/Admin)     |
| `/task-core-api/api/v1/task`                    | GET    | Get user’s own tasks               |
| `/task-core-api/api/v1/task/all`                | GET    | Admin: Get all users’ tasks        |
| `/task-core-api/api/v1/task/{taskId}`           | GET    | Get specific task by ID            |
| `/task-core-api/api/v1/task/{taskId}`           | PUT    | Update task (Owner/Admin)          |
| `/task-core-api/api/v1/task/{taskId}`           | DELETE | Delete task (Owner/Admin)          |

> Example task ID: `TASK-371ac17a-19cd-4372-a8f8-a077b739eea0`

---

## 🛑 Stopping Containers
```bash
docker compose down
```

---

## 📄 License

For educational/demo purposes.
