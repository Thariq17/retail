package com.retailpos.security;

import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

@Provider
@Security
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

	public void filter(ContainerRequestContext requestContext) throws IOException {

		// Get the HTTP Authorization header from the request
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

		// Extract the token from the HTTP Authorization header
		String token = authorizationHeader.substring("Bearer".length()).trim();
		
		try {

			/*
			 * // Validate the token Key key =
			 * KeyGenerator.getInstance("AES").generateKey();
			 * System.out.println(key);
			 * Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			 */

			String[] split_string = token.split("\\.");
			String base64EncodedHeader = split_string[0];
			String base64EncodedBody = split_string[1];
			String base64EncodedSignature = split_string[2];
			Base64 base64Url = new Base64(true);
			String header = new String(base64Url.decode(base64EncodedHeader));
			String body = new String(base64Url.decode(base64EncodedBody));
			JSONObject jsonObject = new JSONObject(body);
			if (!jsonObject.get("sub").equals("retailpos"))
				throw new Exception("InValid Token");
			if (!jsonObject.get("role").equals("admin"))
				throw new Exception("InValid Token");
		} catch (Exception e) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

}
