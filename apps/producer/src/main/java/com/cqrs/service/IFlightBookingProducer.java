package com.cqrs.service;

public interface IFlightBookingProducer {

    void sendMessage(final String message, final String topicName);
}
