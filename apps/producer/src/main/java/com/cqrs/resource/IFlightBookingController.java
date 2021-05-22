package com.cqrs.resource;

import com.cqrs.model.FLightBookingResponse;
import com.cqrs.model.FlightBookingRequest;
import com.cqrs.model.Response;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IFlightBookingController {

    @GetMapping("/")
    String imHealthy();

    @PostMapping("/book")
    Response<List<FLightBookingResponse>> book(@RequestBody final List<FlightBookingRequest> request);

    @DeleteMapping("/cancelBooking/{bookingReferenceId}")
    Response<List<FLightBookingResponse>> deleteBooking(@PathVariable("bookingReferenceId") final String bookingReferenceId) throws Exception;
}
