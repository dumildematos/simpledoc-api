#
# Build stage
#
FROM maven:3.8.2-jdk-18 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:18
COPY --from=build /target/simpledoc-api-0.0.1-SNAPSHOT.jar simpledoc-api.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","simpledoc-api.jar"]