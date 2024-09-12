# Usar una imagen base de Java
FROM openjdk:22
LABEL authors="erik.lopez_pragma"
WORKDIR /app
COPY . .
RUN ./mvnw clean package
RUN mv target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]