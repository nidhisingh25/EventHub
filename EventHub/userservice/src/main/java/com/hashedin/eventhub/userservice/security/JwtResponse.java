package com.hashedin.eventhub.userservice.security;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;

	private boolean isAuthenticated;

	private String message;

	private final String jwtToken;

	public JwtResponse(boolean isAuthenticated, String message, String jwtToken) {
		this.isAuthenticated = isAuthenticated;
		this.message = message;
		this.jwtToken = jwtToken;
	}

	public boolean getIsAuthenticated() { return this.isAuthenticated; }

	public String getToken() {
		return this.jwtToken;
	}

	public String getMessage() { return this.message; }

}
