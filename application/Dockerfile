FROM openjdk:8u131-jre-alpine
#MAINTAINER open-data-tu
ADD . /code

WORKDIR /code

# Dependencies
ADD target/WeatherDataImporter-0.0.1.jar app.jar

ENTRYPOINT ["sh", "app_entrypoint.sh"]

EXPOSE 8080