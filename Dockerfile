FROM icr.io/appcafe/open-liberty:kernel-slim-java17-openj9-ubi

COPY --chown=1001:0 /src/main/liberty/config /config

COPY --chown=1001:0 ibm/wmq.jakarta.jmsra.rar /config

RUN features.sh

COPY --chown=1001:0 target/*.war /config/apps

RUN configure.sh
