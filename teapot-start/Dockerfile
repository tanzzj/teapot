FROM java:8

ARG JAR_NAME=teapot-start-0.0.1.jar
ARG JAR_PORT=9125
ARG JAR_PATH=./target
ARG ENV_JAR=teapot-start.jar

RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
MAINTAINER TanzJ<im.tanzj@live.com>
WORKDIR /main/apps/teapot
ADD ${JAR_PATH}/${JAR_NAME} /${ENV_JAR}

EXPOSE  ${JAR_PORT}

ENTRYPOINT "java" "-jar" /"teapot-start.jar"
