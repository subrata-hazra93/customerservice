FROM adoptopenjdk/openjdk11:jdk-11.0.16_8-alpine-slim
LABEL maintainer=subrata_hazra@persistent.com
RUN addgroup -S customerservicegroup && adduser -S customerserviceuser -G customerservicegroup
USER customerserviceuser
WORKDIR customerservice
ADD build/libs/customerservice-0.1-all.jar customerservice.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "customerservice.jar"]