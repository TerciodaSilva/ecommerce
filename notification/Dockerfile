FROM maven:3.9.5 AS build

WORKDIR /notification

COPY pom.xml pom.xml
COPY src src
RUN mvn clean install -DskipTests


FROM maven:3.9.5
COPY --from=build /notification/target/*.jar notification.jar
ENTRYPOINT java -jar notification.jar