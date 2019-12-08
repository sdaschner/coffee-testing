FROM adoptopenjdk/openjdk13-openj9:x86_64-alpine-jre-13.0.1_9_openj9-0.17.0
RUN apk add curl

COPY target/lib/* /deployments/lib/
COPY target/*-runner.jar /deployments/app.jar

CMD ["java", "-jar", "-Dquarkus.http.host=0.0.0.0", "-Djava.util.logging.manager=org.jboss.logmanager.LogManager", "/deployments/app.jar"]
