FROM amazoncorretto:11.0.4

LABEL maintainer="Alex Popa <popaalexovidiu@gmail.com>"

VOLUME /tmp

ARG JAR_FILE=target/goodssharingapi-1.0-SNAPSHOT.jar

COPY ${JAR_FILE} goodssharingapi.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/goodssharingapi.jar","--spring.profiles.active=dev"]
