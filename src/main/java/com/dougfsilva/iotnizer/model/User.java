package com.dougfsilva.iotnizer.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class User {

    private String id;
    private Email email;
    private String name;
    private String password;
    private List<Profile> profiles;
    private String clientMqttPassword;
    private Boolean blocked;

}
