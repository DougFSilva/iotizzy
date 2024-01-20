package com.dougfsilva.iotizzy.exception;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
