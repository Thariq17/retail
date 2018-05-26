package com.retailpos.controller;

import java.util.Date;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;

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
	public User saveNewUser(User user) throws Exception {
		int i=userService.saveNewUser(user);
		user.setId(i);
		return user;		
	}
	
	@GET
	@Path("/getUserById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Security
	public User getUserBbyId(@PathParam("id") int id){
		User user=userService.getUserById(id);
		return user;
	}
	
	@DELETE
	@Path("/deleteUserById/{id}")
	@Security
	public void deleteUserbyId(@PathParam("id") int id) throws Exception{
		userService.deleteUserById(id);		
	}
	
	@PUT
	@Path("/updateUserById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Security
	public User updateUserById(User user,@PathParam("id")int id) throws Exception{
		User UpdatedUser=userService.updateUserById(user,id);
		return UpdatedUser;
	}

}
