FROM maven:3.5-jdk-8
#MAINTAINER open-data-tu
ADD . /code

WORKDIR /code

RUN mvn clean install

# Dependencies
ADD target/WeatherDataImporter-0.0.1.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
