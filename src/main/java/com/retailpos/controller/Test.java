package com.retailpos.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import com.retailpos.security.Security;

@Path("/test")
public class Test {
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
}
