FROM adoptopenjdk/openjdk14-openj9:x86_64-alpine-jre-14_36.1_openj9-0.19.0
RUN apk add curl

COPY target/lib/* /deployments/lib/
COPY target/*-runner.jar /deployments/app.jar

CMD ["java", "-jar", "-Dquarkus.http.host=0.0.0.0", "-Djava.util.logging.manager=org.jboss.logmanager.LogManager", "/deployments/app.jar"]
