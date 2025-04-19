# Taxi Fleet Communication System

The software that implements the communication for a taxi fleet company which provides user booking capabilities, real time notifications to the taxi about booking requests and admin dashboard reports as well.

---

## Tech Stack

- Java 21 for all the modules.
- Spring boot web application for the Web module.
- Server-Sent Events (SSE) for client notifications.
- Angular 19 for the front end application
- Postgres DB, Hibernate and MapStruct for the persistence layer.
- Java Service loader to provide dynamic Persistence layer implementation.
- Docker & Docker Compose for containerization.

---

## Folder Structure
/TaxiFleetCommunication
    ├── Design/ # Design documents and diagrams 
    ├── Requirements/ # Requirement specifications 
    ├── Sources/ # Source code directory
        ├── com.pal.taxi.management/ # Spring Boot backend modules
            ├── common # module for common functionalities such as Common Model classes, Exceptions, utilities for thread Locks and Validation.
            ├── user # Module for user related functionalities.
            ├── taxi # Module for taxi related functionalities
            ├── system # The central and important Module for Taxi fleet management system, which contains all the core implementations of system.
            ├── persistence # Module provides functionalities for persisting data onto the Postgres DB. This is aplugable module, could be swapped by any persistence module based implementation.
            ├── management-webapp # The web app contains various controllers and services.
        └── taxi-fleet-frontend/ # Angular frontend application

## Running with Docker

Clone the Git repository and run the following command.

Rebuild and start the application by running the following command. The command internally installs the container and compiles the backend and frontened and invokes both the backend and frontend.

```
docker-compose down --volumes --remove-orphans && docker-compose build --no-cache && docker-compose up
```

## Accessing the Application
Frontend: 
```
http://localhost:4200
```