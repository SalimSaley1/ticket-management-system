FROM openjdk:17-jdk-alpine

# add the jar file into the docker image
ADD target/ticketsystem-0.0.1-SNAPSHOT.jar app.jar

# run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]