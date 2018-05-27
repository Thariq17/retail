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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import com.retailpos.dao.UserDao;
import com.retailpos.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("/token")
public class TokenGenerator {

	@Autowired
	UserDao dao;

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateUser(User user) throws Exception {

		// Authenticate the user using the credentials provided
		User u = dao.authenticate(user.getUsername(), user.getPassword(), user.getLocation());
		Boolean authencate = BCrypt.checkpw(user.getPassword(), u.getPassword());

		// Issue a token for the user
		if (authencate) {
			String token = issueToken("retailpos", u.getUsername(), u.getLocation(), u.getRole().getRole());
			// Return the token on the response
			return Response.ok().header("AUTHORIZATION", "Bearer " + token).build();
		} else {
			throw new Exception("Password id wrong");
		}

	}

	private String issueToken(String subject, String username, String location, String role)
			throws NoSuchAlgorithmException {
		Key key = KeyGenerator.getInstance("AES").generateKey();
		String jwtToken = Jwts.builder().setSubject(subject).claim("username", username)
				// .setIssuer(uriInfo.getAbsolutePath().toString())
				.claim("location", location).claim("role", role).setIssuedAt(CurrentDate())
				.setExpiration(addFifteenMin(CurrentDate())).signWith(SignatureAlgorithm.HS512, key).compact();
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
