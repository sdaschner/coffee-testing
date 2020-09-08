#FROM sdaschner/open-liberty:javaee8-postgres-jdk12-b1




FROM openliberty/open-liberty:kernel-java14-openj9-ubi

ADD --chown=1001:0 https://repo.maven.apache.org/maven2/org/postgresql/postgresql/9.4.1212/postgresql-9.4.1212.jar /liberty/lib/postgresql-9.4.1212.jar


# Add config
#COPY --chown=1001:0  target/liberty/wlp/usr/servers/defaultServer/server.xml /config/
#COPY --chown=1001:0  target/liberty/wlp/usr/servers/defaultServer/bootstrap.properties /config/
#COPY --chown=1001:0  target/liberty/wlp/usr/servers/defaultServer/configDropins/overrides/liberty-plugin-variable-config.xml /config/configDropins/overrides/

COPY --chown=1001:0 src/main/liberty/config/server.xml /config/
COPY --chown=1001:0 src/main/liberty/config/configDropins/defaults/postgres-driver.xml /config/configDropins/defaults/
#COPY --chown=1001:0 target/coffee-shop.war /config/apps/
COPY --chown=1001:0 target/coffee-shop.war /config/dropins/

# Add application
#COPY --chown=1001:0  target/liberty/wlp/usr/servers/defaultServer/apps/demo-devmode-maven.war /config/apps/
