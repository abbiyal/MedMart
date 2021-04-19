package medgateway.models;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.NotNull;

import javax.persistence.JoinColumn;

@Entity
@Table(name="users")
public class Users {
	
	@Id
	@Column(name="USER_NAME",unique=true)
	private String username;
	
	@Column(name="name")
	private String name;
	
	@Column(name="Password")
	private String password;
	@Column(name="Address")
	private String address;
	@Column(name="Phone",unique=true)
	private String phone;
	@NotNull
	private String role;
	@Column(name="ResetToken",unique=true)
	private String resetToken;
	
	
	
	public Users() {
		
	}
	
	
	public Users(String username, String name, String password, String address, String phone, String role,
			String resetToken) {
		
		this.username = username;
		this.name = name;
		this.password = password;
		this.address = address;
		this.phone = phone;
		this.role = role;
		this.resetToken = resetToken;
	}


	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getResetToken() {
		return resetToken;
	}
	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
	@Override
	public String toString() {
		return "Users [username=" + username + ", name=" + name + ", password=" + password + ", address=" + address
				+ ", phone=" + phone + ", role=" + role + ", resetToken=" + resetToken + "]";
	}
	
	



}
