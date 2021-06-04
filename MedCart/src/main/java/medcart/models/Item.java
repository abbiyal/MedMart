package medcart.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name="Cart_Item")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull
	private String productId;
	@OneToOne(fetch=FetchType.EAGER)
	private Cart cart;
	@NotNull
	private int quantity;
	@NotNull
	private double price;
	private String productName;
	
	
	public Item() {
		
	}

	public Item(String productId,  int quantity, double price, String productName) {
		
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
		this.productName = productName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", productId=" + productId + ", cart=" + cart + ", quantity=" + quantity + ", price="
				+ price + ", productName=" + productName + "]";
	}
	
	
		
	
}
