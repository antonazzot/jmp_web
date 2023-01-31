FROM maven:3.8.5-openjdk-17-slim AS build

ADD ./pom.xml pom.xml
ADD ./kustom.env kustom.env
ADD ./src src/

#VOLUME /var/run/docker.sock:/var/run/docker.sock

RUN mvn clean package -Dmaven.test.skip

FROM openjdk:17-alpine
COPY --from=build target/jmpwep-0.0.1-SNAPSHOT.jar application.jar

EXPOSE 8989

CMD java -jar application.jar

