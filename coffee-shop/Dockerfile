FROM sdaschner/open-liberty:javaee8-postgres-jdk12-b1

COPY deployment/liberty/server.xml $CONFIG_DIR

COPY target/coffee-shop.war $DEPLOYMENT_DIR
