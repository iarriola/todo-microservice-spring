FROM maven:3.6.3-jdk-11 as build
WORKDIR /b
COPY ./ .
RUN mvn package

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /a
COPY --from=build /b/target/todo-micorservice-0.0.1-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java","-jar","/a/app.jar"]