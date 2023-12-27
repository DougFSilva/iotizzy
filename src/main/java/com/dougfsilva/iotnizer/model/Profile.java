package com.dougfsilva.iotnizer.model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "profileType")
public class Profile {

    private ProfileType profileType;
}
