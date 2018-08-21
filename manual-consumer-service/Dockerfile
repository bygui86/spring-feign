
FROM openjdk:8-jre-alpine

ADD target/ /opt/
WORKDIR /opt

EXPOSE 8083

ENTRYPOINT exec java $JAVA_OPTS -jar manual-consumer-service.jar
