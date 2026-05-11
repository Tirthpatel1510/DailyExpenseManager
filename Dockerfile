# Build Stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Run Stage
FROM tomcat:10.1-jdk17-temurin-slim
# Remove default apps to avoid conflicts
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /target/DailyExpenseManager.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
