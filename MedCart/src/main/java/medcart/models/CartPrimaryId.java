package medcart.models;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.sun.istack.NotNull;


public class CartPrimaryId implements Serializable{
		
	private long cartId;
	private String userId;
	
	
	public CartPrimaryId() {
		
	}


	public CartPrimaryId(long cartId, String userId) {
		this.cartId = cartId;
		this.userId = userId;
	}
	
	
}