package com.cqrs.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "bookings")
@Data
public class Booking {

    @Transient
    public static final String SEQIENCE_NAME = "BOOKING_SEQUENCE";

    @Id
    private Long id;
    private String name;
    private String gender;
    private String referenceId;

}
