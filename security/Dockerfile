FROM maven:3.9.5 AS build

WORKDIR /security

COPY pom.xml pom.xml
COPY src src
RUN mvn clean install -DskipTests


FROM maven:3.9.5
COPY --from=build /security/target/*.jar security.jar
ENTRYPOINT java -jar security.jar