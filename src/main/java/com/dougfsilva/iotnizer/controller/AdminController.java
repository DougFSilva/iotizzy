package com.dougfsilva.iotnizer.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dougfsilva.iotnizer.dto.DetailedUserDto;
import com.dougfsilva.iotnizer.dto.UserDto;
import com.dougfsilva.iotnizer.model.User;
import com.dougfsilva.iotnizer.service.admin.BlockUser;
import com.dougfsilva.iotnizer.service.admin.DeleteUserAsAdmin;
import com.dougfsilva.iotnizer.service.admin.FindUserAsAdmin;
import com.dougfsilva.iotnizer.service.admin.UpdateUserProfile;

@RestController
@RequestMapping("/admin")
public class AdminController {

	private final FindUserAsAdmin findUser;

	private final DeleteUserAsAdmin deleteUser;

	private final UpdateUserProfile updateProfile;

	private final BlockUser blockUser;

	public AdminController(FindUserAsAdmin findUser, DeleteUserAsAdmin deleteUser, UpdateUserProfile updateProfile,
			BlockUser blockUser) {
		this.findUser = findUser;
		this.deleteUser = deleteUser;
		this.updateProfile = updateProfile;
		this.blockUser = blockUser;
	}
	
	@DeleteMapping("/user")
	public ResponseEntity<Void> deleteUser(@RequestParam("id") String id){
		deleteUser.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/user/update-profile")
	public ResponseEntity<UserDto> updateUserProfile(@RequestParam("id") String id, @RequestParam("cod") Long profileCod){
		User user = updateProfile.updateProfile(id, profileCod);
		return ResponseEntity.ok().body(new UserDto(user));
	}

	@PutMapping("/user/block")
	public ResponseEntity<UserDto> blockUser(@RequestParam("id") String id, @RequestParam("status") Boolean status) {
		User user = blockUser.block(id, status);
		return ResponseEntity.ok().body(new UserDto(user));
	}
	
	@GetMapping("/user")
	public ResponseEntity<DetailedUserDto> findUserById(@RequestParam("id") String id) {
		DetailedUserDto user = findUser.findByIdDetailed(id);
		return ResponseEntity.ok().body(user);
	}

	@GetMapping("/user/all")
	public ResponseEntity<List<UserDto>> findAllUsers() {
		List<User> users = findUser.findAll();
		List<UserDto> usersDto = users.stream().map(UserDto::new).toList();
		return ResponseEntity.ok().body(usersDto);
	}

}
