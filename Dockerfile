FROM adoptopenjdk/openjdk11:centos-slim

ARG JAR_FILE=build/libs/trader-malchooni.jar
COPY ${JAR_FILE} trader-malchooni.jar
ENTRYPOINT ["java","-jar","-Dfile.encoding=UTF-8","-Duser.timezone=Asia/Seoul","/trader-malchooni.jar"]

EXPOSE 8080