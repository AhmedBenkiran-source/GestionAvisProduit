package com.example.avis.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.avis.model.User;
import com.example.avis.repository.UserRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userRepoitory;

	@GetMapping("/all")
	public List<User> getAllUsers() {
		ArrayList<User> user = new ArrayList<User>();

		for (User element : userRepoitory.findAll()) {
			if(!element.getUsername().equals("amdin")) {
				user.add(element);
			}
		}
		return user;
	}

	@GetMapping("/{email}")
	public User getUser(@PathVariable String email) {
		return userRepoitory.findByUsernameOrEmail(email);
	}

	@PutMapping("/update")
	public User updateUser(@RequestBody String profile) throws JsonParseException, JsonMappingException, IOException {
		User use = new ObjectMapper().readValue(profile, User.class);

		Optional<User> user = this.userRepoitory.findById(use.getId());
		user.get().setUsername(use.getUsername());
		user.get().setEmail(use.getEmail());
		return userRepoitory.save(user.get());
	}

}
