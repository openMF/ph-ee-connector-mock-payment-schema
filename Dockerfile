FROM openjdk:17.0.2-jdk-slim-bullseye
EXPOSE 5000

COPY build/libs/*.jar .
CMD java -jar *.jar
