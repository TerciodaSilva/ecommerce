FROM maven:3.9.5 AS build

WORKDIR /project

COPY pom.xml pom.xml
COPY src src
RUN mvn clean install -DskipTests


FROM maven:3.9.5
COPY --from=build /project/target/*.jar project.jar
ENTRYPOINT java -jar project.jar