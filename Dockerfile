#
FROM openjdk:17
EXPOSE 8085
COPY ./target/his_service.jar ./
WORKDIR ./
ENTRYPOINT ["java","-jar","/his_service.jar"]

