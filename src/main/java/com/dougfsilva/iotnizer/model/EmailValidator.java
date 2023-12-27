package com.dougfsilva.iotnizer.model;

import com.dougfsilva.iotnizer.exception.InvalidEmailException;
import lombok.Getter;

public class EmailValidator {

    public void validate(String address) {
        if (address == null || !address.matches("^[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {

            throw new InvalidEmailException("Email " + address + " is not valid!");
        }
    }

}
