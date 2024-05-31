# Usar la imagen base de WildFly
FROM quay.io/wildfly/wildfly:30.0.1.Final-jdk17

# Copiar el archivo de configuración de WildFly
COPY standalone-microprofile-jaeger.xml /opt/jboss/wildfly/standalone/configuration/

# Copiar la aplicación WAR al directorio de despliegues de WildFly
COPY target/demo63.war ${JBOSS_HOME}/standalone/deployments/

# Crear usuario de WildFly
RUN /opt/jboss/wildfly/bin/add-user.sh pablo pablo --silent

# Definir variables de entorno
ENV WILDFLY_USER pablo
ENV WILDFLY_PASS pablo
ENV DS_NAME PostgreSQLDS
ENV DS_USER postgres
ENV DS_PASS postgres
ENV DS_URI jdbc:postgresql://postgres:5432/clientesdb
ENV JBOSS_CLI $JBOSS_HOME/bin/jboss-cli.sh
ENV DEPLOYMENT_DIR $JBOSS_HOME/standalone/deployments/

# Configurar el servidor WildFly
RUN echo "Starting WildFly server" && \
      bash -c '$JBOSS_HOME/bin/standalone.sh -c standalone.xml &' && \
      bash -c 'until $JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running; do echo $JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null; sleep 1; done' && \
      curl --location --output /tmp/postgresql-42.2.16.jar --url https://jdbc.postgresql.org/download/postgresql-42.2.16.jar && \
      $JBOSS_CLI --connect --command="/extension=org.wildfly.extension.microprofile.openapi-smallrye:add" && \
      $JBOSS_CLI --connect --command="/subsystem=microprofile-openapi-smallrye:add" && \
      $JBOSS_CLI --connect --command="/subsystem=undertow/server=default-server/ajp-listener=ajp-listener:add(socket-binding=ajp, scheme=https, enabled=true)" && \
      $JBOSS_CLI --connect --command="/subsystem=undertow:write-attribute(name=statistics-enabled,value=true)" && \
      $JBOSS_CLI --connect --command="module add --name=org.postgres --resources=/tmp/postgresql-42.2.16.jar --dependencies=javax.api,javax.transaction.api" && \
      $JBOSS_CLI --connect --command="/subsystem=datasources/jdbc-driver=postgres:add(driver-name=postgres,driver-module-name=org.postgres,driver-class-name=org.postgresql.Driver)" && \
      $JBOSS_CLI --connect --command="data-source add \
        --name=${DS_NAME} \
        --jndi-name=java:jboss/datasources/${DS_NAME} \
        --user-name=${DS_USER} \
        --password=${DS_PASS} \
        --driver-name=postgres \
        --connection-url=${DS_URI} \
        --min-pool-size=10 \
        --max-pool-size=20 \
        --blocking-timeout-wait-millis=5000 \
        --statistics-enabled=true \
        --enabled=true" && \
      $JBOSS_CLI --connect --command=":shutdown" && \
      rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/ $JBOSS_HOME/standalone/log/* && \
      rm -f /tmp/*.jar

# Exponer los puertos necesarios
EXPOSE 8080
#EXPOSE 9990

# Comando para iniciar WildFly con la configuración específica
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]