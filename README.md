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

* kafka.metadata-broker-list
* kafka.serializer-class
* kafka.partitioner-class
* kafka.request-required-acks
* kafka.bootstrap-servers 
* kafka.key-serializer
* kafka.value-serializer
* kafka.topic


### Resource Properties

* resource.url

--------------------------------

## How to RUN

- Start Zookeeper
- Start Kafka
  - create topic: weatherData
- docker build . 


