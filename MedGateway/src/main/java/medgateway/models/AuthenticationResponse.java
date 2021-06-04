package medgateway.models;

public class AuthenticationResponse {
	
	private final String jwt;
	private final String roles;
	private final String name;
	private final String phoneno;
	public AuthenticationResponse(String jwt, String roles, String name, String phoneno) {
		super();
		this.jwt = jwt;
		this.roles = roles;
		this.name = name;
		this.phoneno = phoneno;
	}
	public String getJwt() {
		return jwt;
	}
	public String getRoles() {
		return roles;
	}
	public String getName() {
		return name;
	}
	public String getPhoneno() {
		return phoneno;
	}


}
