package com.cqrs.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {

    private String message;
    private int code;
    private T body;
}
