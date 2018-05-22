package com.retailpos.controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.retailpos.dao.TestDa;
import com.retailpos.model.User;
import com.retailpos.security.Security;

@Path("/test")
public class Test {
	@Autowired
	TestDa  da;
	
	
	@GET
	@Path("/demo")
	public Response savePayment() {
		String result = "srdgfdgf";
		return Response.status(200).entity(result).build();
	}

	@GET
	@Path("/demo1")
	@Security
	public Response savePayment1() {
		String result = "srdgfdgf";
		return Response.status(200).entity(result).build();

	}
	
	
	@POST
	@Path("/demo2")
	@Security
	public Response savePayment2(User user) {
		String result = user.getLocation();
		
		System.out.println(result);
		da.addUser(user);
		
		return Response.status(200).entity(result).build();

	}
}
