package com.dougfsilva.iotizzy.exception;

import java.io.Serial;

public class OperationNotPermittedException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public OperationNotPermittedException(String message) {
        super(message);
    }
}
