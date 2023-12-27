package com.dougfsilva.iotnizer.model;

import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class User {

    private String id;
    private String email;
    private String name;
    private String password;
    private List<Profile> profiles;
    private String clientMqttPassword;
    private Boolean blocked;

    public User(String id, String email, String name, String password, List<Profile> profiles, String clientMqttPassword, Boolean blocked) {
        this.id = id;
        EmailValidator emailValidator = new EmailValidator();
        emailValidator.validate(email);
        this.email = email;
        this.name = name;
        this.password = password;
        this.profiles = profiles;
        this.clientMqttPassword = clientMqttPassword;
        this.blocked = blocked;
    }
}
