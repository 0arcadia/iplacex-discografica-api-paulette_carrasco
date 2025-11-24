FROM gradle:8.10-jdk21 AS build
WORKDIR /app

COPY . .

RUN gradle clean build -x test

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

ENV PORT=8080

COPY --from=build /app/build/libs/discografia-1.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
