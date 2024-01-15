package com.dougfsilva.iotizzy.exception;

import java.io.Serial;

public class MqttFailException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public MqttFailException(String message) {
        super(message);
    }
}