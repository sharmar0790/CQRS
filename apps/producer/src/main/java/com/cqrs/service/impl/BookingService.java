package com.cqrs.service.impl;

import com.cqrs.domain.Booking;
import com.cqrs.service.IBookingService;
import com.cqrs.model.FLightBookingResponse;
import com.cqrs.model.FlightBookingRequest;
import com.cqrs.repository.BookingRepository;
import com.cqrs.sequencegenerator.SequenceGeneratorService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class BookingService implements IBookingService {

    private static final Logger LOG = LoggerFactory.getLogger(BookingService.class);
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy", Locale.ENGLISH);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    public List<FLightBookingResponse> book(final List<FlightBookingRequest> request) {

        List<FLightBookingResponse> response = new ArrayList<>();

        try {
            for (FlightBookingRequest req : request) {
                final Booking booking = new Booking();
                booking.setId(sequenceGeneratorService.generateSequence(Booking.SEQIENCE_NAME));
                booking.setGender(req.getGender());
                booking.setName(req.getName());
                LocalDateTime ldt = LocalDateTime.now();
                String referenceId = UUID.randomUUID().toString() + dtf.format(ldt);
                LOG.info("Ref ID : {}", referenceId);
                booking.setReferenceId(referenceId);
                bookingRepository.save(booking);


                response.add(new FLightBookingResponse(req.getName(), req.getGender(), booking.getReferenceId()));
            }
            return response;
        } catch (Exception ex) {
            LOG.error("Error caught while persisting the data into mongodb {}", ExceptionUtils.getStackTrace(ex));
            return response;
        }
    }


    public boolean deleteBooking(String refId) {
        try {
            final Booking booking = bookingRepository.findByRefId(refId);
            bookingRepository.deleteById(booking.getId());

            return true;
        } catch (Exception e) {
            LOG.error("Got error while deleting a booking : {}, id {}", refId, e);

            return false;
        }
    }
}
