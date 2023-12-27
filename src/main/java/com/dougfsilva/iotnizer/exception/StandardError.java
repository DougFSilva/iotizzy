package com.dougfsilva.iotnizer.exception;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StandardError  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private long timestamp;
    private Integer status;
    private String error;

}
