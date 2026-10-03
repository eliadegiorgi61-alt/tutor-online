FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
FROM eclipse-temurin:17
WORKDIR /app
COPY --from=build /app/target/tutor-online-1.0.jar app.jar
EXPOSE 10000
CMD ["java", "-jar", "app.jar"]
