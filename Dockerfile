FROM amazoncorretto:17.0.4-al2 as builder
ADD ./pom.xml pom.xml
ADD ./kustom.env kustom.env
ADD ./src src/

RUN mvn clean package

FROM amazoncorretto:17.0.4-al2
COPY --from=builder target/jmpwep-0.0.1-SNAPSHOT.jar application.jar

EXPOSE 8989

CMD java -jar application.jar

