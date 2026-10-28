# HRMS Database API

Spring Boot API for employee records and attendance management.

## Requirements

- Java 17 or newer
- MySQL

## Configure database

Set these environment variables before running the application:

- `DB_URL` (defaults to the local `hclhrmspractice` database)
- `DB_USERNAME` (defaults to `root`)
- `DB_PASSWORD`

## Run

```powershell
.\mvnw.cmd spring-boot:run
```

The API listens on port `8082` by default. Project package structure is shown in [the project structure screenshot](docs/images/project-structure.png).
