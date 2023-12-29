package com.dougfsilva.iotnizer.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dougfsilva.iotnizer.dto.UserDto;
import com.dougfsilva.iotnizer.dto.CreateUserForm;
import com.dougfsilva.iotnizer.dto.UpdateUserForm;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.service.user.CreateUser;
import com.dougfsilva.iotnizer.service.user.DeleteUser;
import com.dougfsilva.iotnizer.service.user.FindUser;
import com.dougfsilva.iotnizer.service.user.UpdateUser;

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
    public ResponseEntity<String> createUser(@RequestBody @Valid CreateUserForm form){
        String id = createUser.create(form.email(), form.name(), form.password(), form.profileType());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam("id") String id){
        deleteUser.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestParam("id") String id, @RequestBody UpdateUserForm form){
    	User user = updateUser.update(id, form.name(), form.email(), form.profileType());
    	return ResponseEntity.ok().body(new UserDto(user));
    }

    @GetMapping
    public ResponseEntity<UserDto> findUser(@RequestParam("id") String id){
        User user = findUser.findById(id);
        return ResponseEntity.ok().body(new UserDto(user));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> findAllUsers(){
    	List<User> users = findUser.findAll();
    	List<UserDto> usersDto = users.stream().map(UserDto::new).toList();
    	return ResponseEntity.ok().body(usersDto);
    }
    
    
}
