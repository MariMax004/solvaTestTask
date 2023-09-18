FROM openjdk:17

WORKDIR /app

COPY build/libs/solvaTask-0.0.1-SNAPSHOT.jar /app
COPY src/main/resources/db/migration/*.sql /app/sql-scripts/

EXPOSE 8080

ENTRYPOINT ["java", "-jar","solvaTask-0.0.1-SNAPSHOT.jar"]