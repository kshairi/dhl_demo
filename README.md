# dhl_demo
calculate distance between geolocation

# UK Postcode Distance Calculator

A Spring Boot microservice that calculates the geographic distance between two UK postcodes using MySQL and Docker.

## Features

- REST API to calculate distances between UK postcodes
- Automatic data loading from mysql data source
- MySQL database integration
- Docker containerization

## Technology Stack

- Java 8++
- Maven
- Docker Desktop
- Spring Boot 2.7.x
- MySQL 8.0 (included in Docker setup)
- Maven

## Getting Started

1. Clone the repository
2. Unzip the 00-init.zip in the resource folder
3. Run docker-compose.yml file.
4. Wait for the MySQL to finish with initial load with 1.8 million record loaded to the MySQL
5. Start the application

## How to use it
- use the endpoint http://localhost:8100/postcode/calculate-distance

### Below is the example of the payload
```
{
    "unitOfMeasure": "km",
    "postcodes" : ["M2 5DB", "W1W 7LT", "L4 0TH"]
}
```

### Below is the example of the response
```
{
    "unitOfMeasure": "km",
    "postcodes" : ["M2 5DB", "W1W 7LT", "L4 0TH"]
}
```
The endpoint can support multiple postcode, meaning it will calculate the distance between one postcode to another postcode based on the sequence from the list

