# MongoDB & Kafka Docker end to end example

### Requirements
  - Docker 18.09+
  - Docker compose 1.24+
  - *nix system

### Running the example

To run the example: `./run.sh` which will:
  
  - Run `docker-compose up` 
  - Wait for MongoDB, Kafka, Kafka Connect to be ready
  - Register the MongoDB Kafka Sink Connector
  - Register the MongoDB Kafka Source Connector
  - Write the events to MongoDB  


### Execute inside the kafka container
Execute ``docker exec -it broker bash``

### List the topics
`kafka-topics --zookeeper zookeeper:2181 --list`


### Consume the message from the topics
Execute `kafka-console-consumer --bootstrap-server broker:9092 --topic booking --group my-app` 


### docker-compose.yml

The following systems will be created:

  - Zookeeper
  - Kafka
  - Confluent Schema Registry
  - Confluent Kafka Connect
  - MongoDB - a 3 node replicaset

---

### Mongo sink db

```json

    rs0:PRIMARY> db.sink.find()
    { "_id" : ObjectId("60a96d8628677a34239ef8d6"), "size" : NumberLong(1), "opp" : "ADD" }
    { "_id" : ObjectId("60a9752828677a34239ef8d8"), "size" : NumberLong(1), "opp" : "BOOKED" }
    { "_id" : ObjectId("60a9756328677a34239ef8d9"), "size" : NumberLong(2), "opp" : "BOOKED" }
    { "_id" : ObjectId("60a975ad28677a34239ef8da"), "size" : NumberLong(1), "opp" : "REMOVED" }

```


### Kafka consumer

```json

    root@broker:/# kafka-console-consumer --bootstrap-server broker:9092 --topic booking --group my-app --from-beginning
    {"size":1,"opp":"ADD"}
    {"size":1,"opp":"BOOKED"}
    {"size":2,"opp":"BOOKED"}
    {"size":1,"opp":"REMOVED"}

```
