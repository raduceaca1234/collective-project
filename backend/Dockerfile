FROM amazoncorretto:11.0.4
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} goodssharingapi.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/goodssharingapi.jar"]