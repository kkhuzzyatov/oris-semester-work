FROM maven:3.9.11-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM tomcat:10.1.29-jdk17
RUN rm -rf /usr/local/tomcat/webapps/ROOT
WORKDIR /usr/local/tomcat/webapps/
COPY --from=builder /app/target/oris-semester-work-1.0-SNAPSHOT.war ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]