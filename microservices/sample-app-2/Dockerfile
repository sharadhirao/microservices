FROM maven:3-amazoncorretto-11 AS build
ARG server_port=port
ARG mongodb_uri=uri
ARG sample_app_1_base_url=url
ENV server_port=$server_port
ENV mongodb_uri=$mongodb_uri
ENV sample_app_1_base_url=$sample_app_1_base_url
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM amazoncorretto:11-alpine-jdk
ARG server_port=port
ARG mongodb_uri=uri
ARG sample_app_1_base_url=url
ENV server_port=$server_port
ENV mongodb_uri=$mongodb_uri
ENV sample_app_1_base_url=$sample_app_1_base_url
COPY --from=build /home/app/target/*.jar /usr/local/lib/sample-app-2.jar
EXPOSE $server_port
ENTRYPOINT ["java","-jar","/usr/local/lib/sample-app-2.jar"]
