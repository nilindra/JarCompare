FROM openjdk:8-jdk-alpine
MAINTAINER Nilindra Fernando <nilindra_99@yahoo.com>

ENV JAPI_VERSION 2.4
ARG JAR_FILE=target/org.jarcompare-0.0.1-SNAPSHOT.jar
ARG LIB_DIR_PATH=target/lib/

VOLUME /workspace
WORKDIR /internal
RUN chmod 755 /internal

RUN apk add --no-cache make perl
RUN wget -q -O - "https://github.com/lvc/japi-compliance-checker/archive/${JAPI_VERSION}.tar.gz" | tar -C /tmp -xzf - && \
  cd "/tmp/japi-compliance-checker-${JAPI_VERSION}" && \
  make install && \
  rm -rf "/tmp/japi-compliance-checker-${JAPI_VERSION}"

COPY ${JAR_FILE} org.jarcompare.jar
COPY ${LIB_DIR_PATH} lib

ENTRYPOINT ["java","-jar","org.jarcompare.jar"]