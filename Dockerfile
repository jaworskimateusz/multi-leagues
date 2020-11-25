FROM openjdk:8-alpine
ADD target/my-leagues-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar my-leagues-0.0.1-SNAPSHOT.jar