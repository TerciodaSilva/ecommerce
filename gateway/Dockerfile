FROM maven:3.9.5 AS build

WORKDIR /gateway

COPY pom.xml pom.xml
COPY src src
RUN mvn clean install -DskipTests


FROM maven:3.9.5
COPY --from=build /gateway/target/*.jar gateway.jar
ENTRYPOINT java -jar gateway.jar