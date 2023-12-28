package com.dougfsilva.iotnizer.model;

import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;
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
