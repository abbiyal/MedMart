package medgateway.models;

public class AuthenticationResponse {
	
	private final String jwt;
	private final String roles;

	public AuthenticationResponse(String jwt,String roles) {
		this.jwt = jwt;
		this.roles=roles;
	}

	public String getJwt() {
		return jwt;
	}

	public String getRoles() {
		return roles;
	}
	
	
	
	

}
