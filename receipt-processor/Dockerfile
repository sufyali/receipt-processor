FROM maven:3.8.4-openjdk-17 AS compilejar
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM openjdk:17
WORKDIR /app
COPY --from=compilejar /app/target/receipt-processor-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "receipt-processor-0.0.1-SNAPSHOT.jar"]