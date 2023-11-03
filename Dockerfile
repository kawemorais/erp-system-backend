FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY build/libs/erp-system-backend-1.0.0.jar erp-system-backend-1.0.0.jar
EXPOSE 8080
CMD ["java","-jar","erp-system-backend-1.0.0.jar"]