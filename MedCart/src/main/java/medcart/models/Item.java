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
	private long itemId;
	@OneToOne(fetch=FetchType.EAGER)
	private Cart cart;
	@NotNull
	private int quantity;
	@NotNull
	private double price;
	private String name;
	private String imageUrl;
		
	public Item() {
		
	}
	public Item(long itemId, Cart cart, int quantity, double price, String name, String imageUrl) {
		this.itemId = itemId;
		this.cart = cart;
		this.quantity = quantity;
		this.price = price;
		this.name = name;
		this.imageUrl = imageUrl;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	@Override
	public String toString() {
		return "Item [id=" + id + ", itemId=" + itemId + ", cart=" + cart + ", quantity=" + quantity + ", price="
				+ price + ", name=" + name + ", imageUrl=" + imageUrl + "]";
	}
}
