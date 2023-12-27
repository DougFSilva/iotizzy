package com.dougfsilva.iotnizer.exception;

import java.io.Serial;

public class ObjectNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException(String message) {
        super(message);
    }
}