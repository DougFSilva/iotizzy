package com.dougfsilva.iotnizer.model;

import lombok.Getter;

@Getter
public enum ControlDeviceType {

	ANALOG(1, "ANALOG"), DISCRETE(2, "DISCRETE");

    private long cod;
    private String description;

    private ControlDeviceType(long cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public static ControlDeviceType toEnum(String description) {
        if (description == null) {
            return null;
        }
        for (ControlDeviceType x : ControlDeviceType.values()) {
            if (description.equals(x.getDescription())) {
                return x;
            }

        }
        throw new IllegalArgumentException("Invalid profile!");
    }
}
