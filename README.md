
# Weather Data Importer + Kafka Data Producer
WeatherDataImporter + Data Producer for Kafka Queue is part of our extensible ETL framework. In this importer, a specific data source is used to extract data from, process them and write them into the Kafka queue.

-----------------------------
## Components
- Data Source
- Batch Jobs
  - Step
      - Read
      - Process
      - Write
- Data Model
- Data Producer for Kafka Queue

-------------------------------

## Poperties for Externalize Configurations 

### Kafka Properties

* kafka.partitioner-class     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
* kafka.acks &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; //defaults to 'all'
* kafka.bootstrap-servers     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; //defaults to localhost:9092
* kafka.key-serializer         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; //defaults to org.apache.kafka.common.serialization.StringSerializer
* kafka.value-serializer      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; //defaults to org.apache.kafka.common.serialization.StringSerializer
* kafka.topic                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; //defaults to weatherData


### Resource Properties

* resource.url  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//Path to the resource url (url contains all the files for one specific day), defaults to the last day 

--------------------------------
## Running:
Inside the root directory of the project run the commands:
```
$ cd application
$ mvn spring-boot:run
```

## Building
For building run the following command either inside the root directory of the project, or inside the 'application' directory:
```
$ mvn clean install
```
the generated jar file is inside application/target directory.

--------------------------------

## Use the docker
#### Pulling the image:
```sh
$ docker pull ahmadjawidjami/luftdaten_info_importer
```
#### Running with sample environment variables:
```sh
$  docker run \
--env "RESOURCE_URL=http://archive.luftdaten.info/2016-12-10/" \
--env "KAFKA_BOOTSTRAP_SERVERS=localhost:9092" \
--env "KAFKA_TOPIC=weatherData" \
ahmadjawidjami/luftdaten_info_importer
```
#### Mandatory enviroment variable:
- KAFKA_BOOTSTRAP_SERVERS


#### Optional environment variable
- KAFKA_TOPIC &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; //Default is 'weatherData'
