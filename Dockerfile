FROM adoptopenjdk/openjdk11:centos-slim

ARG JAR_FILE=build/libs/trader-malchooni-1.0.0.jar
COPY ${JAR_FILE} trader-malchooni-1.0.0.jar
ENTRYPOINT ["java","-jar","-Dfile.encoding=UTF-8","-Duser.timezone=Asia/Seoul","/trader-malchooni-1.0.0.jar"]

EXPOSE 8080