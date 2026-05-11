# Use Maven to build the project
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Use Tomcat 10 for the final image
FROM tomcat:10.1-jdk17-slim
COPY --from=build /target/DailyExpenseManager.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
