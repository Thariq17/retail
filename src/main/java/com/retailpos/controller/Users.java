package com.retailpos.controller;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;

import com.retailpos.dao.TestDa;
import com.retailpos.model.User;
import com.retailpos.security.Security;
import com.retailpos.service.UserService;

@Path("/users")
public class Users {

	@Autowired
	UserService  userService;
	
	@POST
	@Path("/newUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Security
	public User saveNewUser(User user) {
		user.setCreatedAt(new Date().getTime());
		int i=userService.saveNewUser(user);
		user.setId(i);
		return user;
		
	}

}
