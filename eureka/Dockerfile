FROM maven:3.9.5 AS build

WORKDIR /eureka

COPY pom.xml pom.xml
COPY src src
RUN mvn clean install -DskipTests


FROM maven:3.9.5
COPY --from=build /eureka/target/*.jar eureka.jar
ENTRYPOINT java -jar eureka.jar