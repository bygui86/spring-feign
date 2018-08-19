
FROM openjdk:8-jre-alpine

ADD target/ /opt/
WORKDIR /opt

EXPOSE 8082

ENTRYPOINT exec java $JAVA_OPTS -jar producer-service.jar
