# Spring Boot User Management API

A RESTful API for user management built with Spring Boot and MongoDB.

## Features

- Complete user CRUD operations
- MongoDB integration
- Password encryption with Argon2
- Input validation
- Pagination and sorting
- Profile-based configuration (dev/prod)
- Response wrapping
- Hot-reloading for development

## Tech Stack

- Java 23
- Spring Boot 3.4.2
- MongoDB
- Lombok
- Spring Security Crypto (Argon2)
- Maven

## API Endpoints

### Users

- `GET /api/v1/users` - List all users (paginated)
- `GET /api/v1/users/{id}` - Get user by ID
- `POST /api/v1/users` - Create new user
- `PUT /api/v1/users/{id}` - Update user
- `DELETE /api/v1/users/{id}` - Delete user

### User Operations

- `PATCH /api/v1/users/avatar/{id}` - Update user avatar
- `PATCH /api/v1/users/role/{id}` - Update user role
- `PATCH /api/v1/users/verify-email/{code}` - Verify user email
- `PATCH /api/v1/users/password/{id}` - Update password


## Setup

1. Install dependencies:

   - JDK 23
   - MongoDB
   - Maven

2. Configure MongoDB:

Copy `example.env` to `.env` and set the following environment variables:

```env
MONGODB_HOST=localhost
MONGODB_PORT=27017
MONGODB_DATABASE=usermanagement
LOG_LEVEL=ERROR
```

3. Build:

```bash
mvn clean install
```

4. Run:

```bash
mvn spring-boot:run
```

## Development

Run with dev profile:

```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

## Production

Build for production:

```bash
mvn clean install -Pprod
```

## License

MIT
