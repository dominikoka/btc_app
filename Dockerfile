# Etap 1: budowanie JAR/WAR
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /app
COPY . .
RUN mvn dependency:resolve
RUN mvn clean package -DskipTests

# Etap 2: uruchamianie w Tomcacie
FROM tomcat:10.1-jdk17

# usuń domyślne aplikacje Tomcata
RUN rm -rf /usr/local/tomcat/webapps/*

# skopiuj naszą aplikację do webapps
COPY --from=builder /app/target/btc-deploy-test-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/btc.war



EXPOSE 8080
CMD ["catalina.sh", "run"]