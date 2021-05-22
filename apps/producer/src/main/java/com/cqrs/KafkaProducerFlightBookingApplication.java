package com.cqrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.cqrs.repository")
public class KafkaProducerFlightBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerFlightBookingApplication.class, args);
    }

}
