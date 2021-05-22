package com.cqrs.resource.impl;

import com.cqrs.resource.IFlightBookingController;
import com.cqrs.service.impl.BookingService;
import com.cqrs.service.impl.FlightBookingProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.cqrs.model.Count;
import com.cqrs.model.FLightBookingResponse;
import com.cqrs.model.FlightBookingRequest;
import com.cqrs.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FlightBookingController implements IFlightBookingController {

    private static final Logger LOG = LoggerFactory.getLogger(FlightBookingController.class);
    private static final String BLANK = "";
    private static final String BOOKED = "BOOKED";
    private static final String REMOVED = "REMOVED";
    private final static ObjectMapper obj = new ObjectMapper();
    private static final String BOOKING_TOPIC = "booking";

    @Autowired
    private BookingService bookingService;

    @Autowired
    private FlightBookingProducer producer;


    public String imHealthy() {
        return "{\"healthy\": true}";
    }

    public Response<List<FLightBookingResponse>> book(@RequestBody final List<FlightBookingRequest> request) {

        LOG.info("Reques to book a flight {}", request);

        List<FLightBookingResponse> result = bookingService.book(request);
        if (!result.isEmpty()) {
            sendMesage(request.size(), BOOKED);
            return buildRespnse(result, HttpStatus.CREATED, "successfully booked the reservation");
        } else {
            return buildRespnse(result, HttpStatus.BAD_REQUEST, "Something went wrong........Please look at server logs to have more analysis");
        }
    }

    private Response<List<FLightBookingResponse>> buildRespnse(List<FLightBookingResponse> result, HttpStatus status, String message) {
        Response<List<FLightBookingResponse>> response = new Response<>();
        response.setCode(status.value());
        response.setMessage(message);
        response.setBody(result);
        return response;
    }

    private void sendMesage(final Integer size, final String operation) {
        String jsonStr = BLANK;
        try {
            jsonStr = obj.writeValueAsString(size);
            Count count = new Count();
            count.setOpp(operation);
            count.setSize(size);
            jsonStr = obj.writeValueAsString(count);
            LOG.info("json converted request is {} ", jsonStr);
            producer.sendMessage(jsonStr, BOOKING_TOPIC);
        } catch (Exception e) {
            LOG.error("Caught error while converting object to JSON {}, error {}", jsonStr, e);
            e.printStackTrace();
        }
    }

    public Response<List<FLightBookingResponse>> deleteBooking(@PathVariable("bookingReferenceId") final String bookingReferenceId) throws Exception {
        boolean result = bookingService.deleteBooking(bookingReferenceId);
        if (result) {
            sendMesage(1, REMOVED);
            return buildRespnse(new ArrayList<>(), HttpStatus.OK, "Cancelled the booking successfully for the ID - " + bookingReferenceId);
        } else {
            return buildRespnse(new ArrayList<>(), HttpStatus.BAD_REQUEST, "Got error while deleting a booking : " + bookingReferenceId + ". Please see the server logs for more information");
        }
    }
}

