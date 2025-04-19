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
$ ./TaxiFleetCommunication
.
 * [Design](./Design) # Design documents and diagrams 
 * [Requirements](./Requirements) # Requirement specifications
 * [Sources](./Sources) # Source code root directory.
   *  [backend](./com.pal.taxi.management) # Spring Boot backend modules
      *  [common](./common) # module for common functionalities such as Common Model classes, Exceptions, utilities for thread Locks and Validation.
      *  [user](./user) # Module for user related functionalities.
      *  [taxi](./taxi) # Module for taxi related functionalities
      *  [system](./system) # The central and important Module for Taxi fleet management system, which contains all the core implementations of system.
      *  [persistence](./persistence) #Module provides functionalities for persisting data onto the Postgres DB. This is aplugable module, could be replaced by any persistence module based implementation.
   * [frontend](./management-webapp) # The web app contains various controllers and services.

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