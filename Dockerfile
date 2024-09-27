FROM openjdk:17-slim

WORKDIR /app

COPY src/test/java/com/ambrose/tripwonder .

RUN chmod +x ./mvnw

RUN ./mvnw clean package -DskipTests

RUN ./mvnw dependency:go-offline



EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]
