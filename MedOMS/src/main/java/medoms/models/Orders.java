package medoms.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Orders {
	
	@Id
	private int id;
	
	@NotNull
	private String username;
	
	@NotNull
	private String status;
	
	@NotNull
	private double subtotal;
	
	@NotNull
	private double discount;
	
	@NotNull
	private double shipping;
	
	@NotNull
	private double total;
	
	@NotNull
	private String createdAt;

	public Orders(int id, @NotNull String username, @NotNull String status, @NotNull double subtotal,
			@NotNull double discount, @NotNull double shipping, @NotNull double total, @NotNull String createdAt) {
		super();
		this.id = id;
		this.username = username;
		this.status = status;
		this.subtotal = subtotal;
		this.discount = discount;
		this.shipping = shipping;
		this.total = total;
		this.createdAt = createdAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getShipping() {
		return shipping;
	}

	public void setShipping(double shipping) {
		this.shipping = shipping;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", username=" + username + ", status=" + status + ", subtotal=" + subtotal
				+ ", discount=" + discount + ", shipping=" + shipping + ", total=" + total + ", createdAt=" + createdAt
				+ "]";
	}
	
	
	

}
