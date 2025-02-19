package com.trading.mfanalyser.test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

	@Autowired
	private UserRepo userRepo;
	
	@RequestMapping(value = "hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String hello(ModelMap model) {
		
		return "hello";
	}
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register(ModelMap model) {
		// Insert user details into table
		User u = new User();
		u.setUsername("User-"+System.currentTimeMillis());
		u.setEmail("asdf@lsdf.com");
		try {
			userRepo.save(u);
			return userRepo.toString();
		} catch (Exception ex) {
			System.out.println(ex);
			model.addAttribute("user", u);
			model.addAttribute("message", "Sorry! Registration Failed. Please try again!");
			return "register";
		}
	}
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String listUsers(ModelMap model) {
		
		try {
			List<User> result = 
			  StreamSupport.stream(userRepo.findAll().spliterator(), false)
			    .collect(Collectors.toList());
			return result.toString();
		} catch (Exception ex) {
			System.out.println(ex);
			model.addAttribute("message", "Sorry! Registration Failed. Please try again!");
			return "register";
		}
	}
}
