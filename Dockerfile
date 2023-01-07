FROM maven as maven

WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn clean install
RUN mvn package

FROM openjdk:17-jdk-slim
ARG JAR_FILE=webshop-backend-0.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app
COPY .env /opt/app/

ENTRYPOINT [ "java", "-jar", "webshop-backend-0.0.1-SNAPSHOT.jar" ]