package com.cqrs.service;

import com.cqrs.model.FLightBookingResponse;
import com.cqrs.model.FlightBookingRequest;

import java.util.List;


public interface IBookingService {

    List<FLightBookingResponse> book(List<FlightBookingRequest> request);


    boolean deleteBooking(final String refId);

}
