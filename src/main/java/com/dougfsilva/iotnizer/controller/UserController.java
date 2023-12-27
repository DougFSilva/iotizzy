package com.dougfsilva.iotnizer.controller;

import com.dougfsilva.iotnizer.dto.UserForm;
import com.dougfsilva.iotnizer.service.user.CreateUser;
import com.dougfsilva.iotnizer.service.user.DeleteUser;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    private final CreateUser createUser;

    private final DeleteUser deleteUser;

    public UserController(CreateUser createUser, DeleteUser deleteUser) {
        this.createUser = createUser;
        this.deleteUser = deleteUser;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid UserForm form){
        String id = createUser.create(form.email(), form.name(), form.password(), form.profileType());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam("id") String id){
        deleteUser.delete(id);
        return ResponseEntity.noContent().build();
    }
}