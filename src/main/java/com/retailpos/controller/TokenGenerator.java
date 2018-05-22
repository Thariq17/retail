package com.retailpos.controller;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.crypto.KeyGenerator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("/token")
public class TokenGenerator {

	@GET
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response authenticateUser() {
		try {

			// Authenticate the user using the credentials provided
			// want to write login service here
			// authenticate(login, password);

			// Issue a token for the user
			String token = issueToken("retailpos","admin");

			// Return the token on the response
			return Response.ok().header("AUTHORIZATION", "Bearer " + token).build();

		} catch (Exception e) {
			return Response.status(500).build();
		}
	}

	private String issueToken(String subject,String username) throws NoSuchAlgorithmException {
		Key key = KeyGenerator.getInstance("AES").generateKey();
		String jwtToken = Jwts.builder().setSubject(subject).claim("role", "admin").claim("username",username)
				// .setIssuer(uriInfo.getAbsolutePath().toString())
				.setIssuedAt(CurrentDate()).setExpiration(addFifteenMin(CurrentDate()))
				.signWith(SignatureAlgorithm.HS512, key).compact();
		return jwtToken;

	}

	private Date CurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return date;
	}

	private Date addFifteenMin(Date date) {
		// convert date to calendar
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, 15);
		// convert calendar to date
		Date currentDatePlusFifteen = c.getTime();
		return currentDatePlusFifteen;
	}
}
