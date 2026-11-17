# Deployment of SpringBoot App

## Local configuration

Set these environment variables before starting the database and backend. Use unique
strong values for the database password and JWT secret; do not commit them.

```powershell
$env:MYSQL_ROOT_PASSWORD = "<your database password>"
$env:DB_PASSWORD = $env:MYSQL_ROOT_PASSWORD
$env:JWT_SECRET = "<a long random secret>"
```

`DB_URL` defaults to `jdbc:mysql://localhost:3307/jwtdemo`, and `DB_USERNAME` defaults
to `root`. Start the development database with
`docker compose -f docker-compose.dev.yml up -d`.

# Folder Structure

- /backend/Dockerfile
- /backend/.dockerignore
- /frontend/Dockerfile
- /frontend/.dockerignore
- /frontend/nginx.conf
- root/docker-compose.yml
- root/.env
- root/.gitignore

### /backend/Dockerfile

```
# ---------- Build State ---------
FROM maven:3.9-eclipse-temurin-25
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests -B

# ----- Run Stage ---------------
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","app.jar" ]
```

### /backend/.dockerignore

```
target
.git
.idea
*.iml
```

### frontend/Docekerfile

```
# ------- Build Stage -----
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# ---- Serve Stage -----
FROM nginx:alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80

```

### frontend/nginx.conf

```
server{
    listen 80;
    root /usr/share/nginx/html;
    index index.html
    # React single-page app
    location / {
        try_files $url /index.html
    }
    # forward API Calls to the Springboot container
    location /api/ {
        proxy_pass http://backend:8080/api/v1;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }

}
```
