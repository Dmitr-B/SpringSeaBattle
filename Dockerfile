FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080
ARG JAR_FILE=target/springseabattle-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
RUN sh -c 'touch springseabattle-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","/app.jar"]