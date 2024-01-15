package com.dougfsilva.iotizzy.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dougfsilva.iotizzy.dto.CreateUserForm;
import com.dougfsilva.iotizzy.dto.UpdateUserForm;
import com.dougfsilva.iotizzy.dto.UserDto;
import com.dougfsilva.iotizzy.model.User;
import com.dougfsilva.iotizzy.service.user.CreateUser;
import com.dougfsilva.iotizzy.service.user.DeleteUser;
import com.dougfsilva.iotizzy.service.user.FindUser;
import com.dougfsilva.iotizzy.service.user.UpdateUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final CreateUser createUser;
    private final DeleteUser deleteUser;
    private final UpdateUser updateUser;
    private final FindUser findUser;

    public UserController(CreateUser createUser, DeleteUser deleteUser, FindUser findUser, UpdateUser updateUser) {
        this.createUser = createUser;
        this.deleteUser = deleteUser;
        this.updateUser = updateUser;
        this.findUser = findUser;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserForm form){
        User user = createUser.create(form.email(), form.name(), form.password(), form.profileType());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(){
        deleteUser.delete();
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserForm form){
    	User user = updateUser.update(form.name(), form.email());
    	return ResponseEntity.ok().body(new UserDto(user));
    }
    
    @GetMapping
    public ResponseEntity<UserDto> findUser(){
        User user = findUser.findUser();
        return ResponseEntity.ok().body(new UserDto(user));
    }
    
    
}
