FROM openjdk:8-jre-slim

VOLUME /tmp
ADD target/vechainmonitor-0.0.1-SNAPSHOT.jar vechain-monitor.jar
RUN sh -c 'touch /vechain-monitor.jar' && \
    mkdir config

ENV JAVA_OPTS=""

ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Duser.timezone=UTC -jar /vechain-monitor.jar