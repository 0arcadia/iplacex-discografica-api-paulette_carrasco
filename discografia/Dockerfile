### ===== STAGE 1: construir el JAR con el wrapper =====
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copiamos todo el proyecto
COPY . .

# Damos permisos de ejecución al wrapper (porque vienes de Windows)
RUN chmod +x gradlew

# Usamos el wrapper para compilar y generar el .jar
RUN ./gradlew clean build -x test

### ===== STAGE 2: imagen final ligera para ejecutar el JAR =====
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Render pasa la variable PORT, pero dejamos 8080 como default
ENV PORT=8080

# Copiamos el JAR generado en el stage anterior
COPY --from=build /app/build/libs/discografia-1.jar app.jar

# Exponemos el puerto interno del contenedor
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]

