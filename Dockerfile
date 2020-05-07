FROM openjdk:8
EXPOSE 8082
ARG JAR_FILE=/target/*.jar
ADD ${JAR_FILE} moviecatlog-movies.jar
ENTRYPOINT ["java","-jar","moviecatlog-movies.jar"]