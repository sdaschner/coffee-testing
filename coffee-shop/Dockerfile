FROM payara/server-full:5.2021.7-jdk11

USER payara
COPY deployment/payara/postgresql-9.4.1212.jar /opt/payara/appserver/glassfish/domains/domain1/lib
COPY deployment/payara/domain.xml /opt/payara/appserver/glassfish/domains/domain1/config/
COPY target/coffee-shop.war /opt/payara/appserver/glassfish/domains/domain1/autodeploy/
