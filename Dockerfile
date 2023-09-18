FROM maven:3.9.3-eclipse-temurin-17-alpine as builder
WORKDIR /app
COPY pom.xml .
# RUN mvn dependency:go-offline -B
COPY src/ ./src/
RUN mvn package -DskipTests=true

FROM eclipse-temurin:17-jre-alpine as PROD
WORKDIR /prod
COPY --from=builder /app/target/training-licenses-sharing-*.jar /prod/app.jar
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "/prod/app.jar"]