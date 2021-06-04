package medgateway.models;

public class PasswordRequest {
	
	public String email;

	
	public PasswordRequest() {
		
	}

	public PasswordRequest(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
