FROM openjdk:8-jre
MAINTAINER Mafei
COPY target/*.jar config-server.jar
ENTRYPOINT ["java","-jar","/config-server.jar"]
EXPOSE 1010