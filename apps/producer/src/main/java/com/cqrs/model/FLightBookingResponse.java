package com.cqrs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class FLightBookingResponse {

    private String name;
    private String gender;
    private String referenceId;


}
