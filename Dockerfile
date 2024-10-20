# Base image with OpenJDK 17 for the application
FROM openjdk:17-jdk-slim AS base

WORKDIR /app

COPY pom.xml ./

RUN apt-get update && \
    apt-get install -y maven

COPY src ./src
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim AS app

WORKDIR /app

COPY --from=base /app/target/CortexTask-0.0.1-SNAPSHOT.jar app.jar

RUN apt-get update && \
    apt-get install -y mariadb-server

ENV MYSQL_DATABASE=cortexdb \
    MYSQL_USER=dbuser \
    MYSQL_PASSWORD=dbpassword \
    MYSQL_ROOT_PASSWORD=rootpassword

ENV SPRING_DATASOURCE_URL=jdbc:mariadb://localhost:3306/cortexdb \
    SPRING_DATASOURCE_USERNAME=dbuser \
    SPRING_DATASOURCE_PASSWORD=dbpassword

EXPOSE 8080 3306

COPY src/main/resources/db/migration/V1__Create_Excursions_Table.sql /app/script1.sql
COPY src/main/resources/db/migration/V2__Insert_Initial_Excursions.sql /app/script2.sql

COPY start-services.sh /app/start-services.sh
RUN chmod +x /app/start-services.sh

ENTRYPOINT ["/app/start-services.sh"]
