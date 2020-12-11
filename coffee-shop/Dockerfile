FROM openliberty/open-liberty:full-java15-openj9-ubi

ADD --chown=1001:0 https://repo.maven.apache.org/maven2/org/postgresql/postgresql/9.4.1212/postgresql-9.4.1212.jar /liberty/lib/postgresql-9.4.1212.jar
RUN chmod +r /liberty/lib/postgresql-9.4.1212.jar

COPY --chown=1001:0 src/main/liberty/config/server.xml /config/
COPY --chown=1001:0 src/main/liberty/config/configDropins/defaults/postgres-driver.xml /config/configDropins/defaults/
COPY --chown=1001:0 target/coffee-shop.war /config/dropins/