FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . /app

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["sh", "-c", "java -Dserver.port=$PORT -jar target/TaskBoard-0.0.1-SNAPSHOT.jar"]
