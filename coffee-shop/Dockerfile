FROM payara/server-full:5.2021.8-jdk11


COPY --chown=payara:payara deployment/payara/postgresql-42.2.9.jar $CONFIG_DIR
COPY --chown=payara:payara deployment/payara/postboot.txt $CONFIG_DIR

RUN true \
    && ${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} start-domain ${DOMAIN_NAME} \
    && ${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} multimode --file $CONFIG_DIR/postboot.txt \
    && ${PAYARA_DIR}/bin/asadmin --user=${ADMIN_USER} --passwordfile=${PASSWORD_FILE} stop-domain ${DOMAIN_NAME} \
    && rm -rf \
        ${PAYARA_DIR}/glassfish/domains/${DOMAIN_NAME}/osgi-cache \
        ${PAYARA_DIR}/glassfish/domains/${DOMAIN_NAME}/logs \
    && true

COPY --chown=payara:payara target/coffee-shop.war $DEPLOY_DIR
