FROM maven:3-amazoncorretto-21 as builder
RUN mkdir -p /build
WORKDIR /build
COPY pom.xml /build
#COPY settings.xml /build
#RUN mvn --settings settings.xml -B dependency:resolve dependency:resolve-plugins
RUN mvn -B dependency:resolve dependency:resolve-plugins
COPY src /build/src
#RUN mvn --settings settings.xml package -DskipTests
RUN mvn package -DskipTests

FROM amazoncorretto:21
EXPOSE 7662

ARG JAR_FILE=/build/target/*.jar
ARG PROP_FILE=/build/target/classes/application.yml
COPY --from=builder ${JAR_FILE} customer-orders-consumer.jar
COPY --from=builder ${PROP_FILE} application.yml

ENTRYPOINT ["java", "-jar","/customer-orders-consumer.jar"]
