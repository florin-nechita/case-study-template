# E-Commerce API

This project is a Spring Boot application that provides a RESTful API for managing products and orders in an e-commerce system.

## Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL** - Primary database
- **Redis** - Caching/session management
- **Flyway** - Database migrations
- **Docker** - Containerization
- **Lombok** - Reduces boilerplate code

## Project Structure

The application follows a standard layered architecture:

- **Controllers** - Handle HTTP requests and responses
- **Services** - Implement business logic
- **Repositories** - Provide data access
- **Entities** - Represent database tables
- **DTOs** - Transfer data between layers

## Setup Instructions

### Prerequisites

- Docker and Docker Compose
- Java 17 or later
- Maven

### Running the Application

1. Clone the repository
2. Start the required infrastructure using Docker Compose:
   ```
   docker-compose up -d
   ```
3. Build and run the application:
   ```
   ./mvnw spring-boot:run
   ```

## API Documentation

### Product API

#### List all products
```
GET /products
```

#### Get product by ID
```
GET /products/{id}
```

#### Create new product
```
POST /products
Content-Type: application/json

{
  "name": "Product Name",
  "price": 9.99,
  "quantityInStock": 100
}
```

#### Update product
```
PATCH /products/{id}
Content-Type: application/json

{
  "name": "Updated Product Name",
  "price": 19.99,
  "quantityInStock": 50
}
```

#### Delete product
```
DELETE /products/{id}
```

### Order API

#### Cancel order
```
POST /orders/{id}/cancel
```

#### Pay for order
```
POST /orders/{id}/pay
```

## Data Model

### Product
- **id** - Primary key
- **name** - Product name
- **price** - Product price
- **stock_amount** - Quantity in stock
- **createdAt** - Creation timestamp

### Order
- **id** - Primary key
- **status** - Order status (PENDING, CANCELED, EXPIRED, PAID)
- **orderItems** - List of items in the order
- **paidAt** - Payment timestamp
- **createdAt** - Creation timestamp

### OrderItem
- **id** - Primary key
- **order** - Reference to the order
- **product** - Reference to the product
- **quantity** - Quantity of the product
- **createdAt** - Creation timestamp

## Features

- Product management (CRUD operations)
- Order management (create, cancel, pay)
- Stock management
- Automatic release of unpaid orders

## Development Status

The application is currently in development with some features not fully implemented:
- Order creation functionality is incomplete
- Scheduled task for releasing unpaid orders is defined but not implemented

## Configuration

The application can be configured through the `application.properties` file, which includes settings for:
- Database connection
- Connection pool
- Redis
- Flyway migrations