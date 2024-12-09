#FROM openjdk:17-jdk-slim
#ARG JAR_FILE=target/CourseAPIs-1.0-SNAPSHOT.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]



FROM openjdk:17-jdk-slim
WORKDIR /sharan
COPY target/CourseAPIs-1.0-SNAPSHOT.jar jarfolder/api.jar
#ENV LOGGING_FILE_PATH=/logs/api.log
EXPOSE 3010
ENTRYPOINT ["java", "-jar", "jarfolder/api.jar"]

