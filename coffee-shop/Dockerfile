FROM sdaschner/open-liberty:javaee8-postgres-jdk11-b3

COPY deployment/liberty/server.xml $CONFIG_DIR

COPY target/coffee-shop.war $DEPLOYMENT_DIR
