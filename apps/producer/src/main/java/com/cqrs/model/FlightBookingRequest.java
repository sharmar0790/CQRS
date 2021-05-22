package com.cqrs.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FlightBookingRequest {

    private String name;
    private String gender;
    private String referenceId;
}
