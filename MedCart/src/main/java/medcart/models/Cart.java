package medcart.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name="Cart")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull
	private String userId;
	private String sessionId;
	private String status;
	private double value;
	
	
	public Cart() {
		
	}

	public Cart(String userId, String sessionId, String status, double value) {
		this.userId = userId;
		this.sessionId = sessionId;
		this.status = status;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Cart [cartId=" + id + ", userId=" + userId + ", sessionId=" + sessionId + ", status=" + status
				+ ", value=" + value + "]";
	}

	public long getCartId() {
		return id;
	}
	public void setCartId(long cartId) {
		this.id = cartId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}

}
