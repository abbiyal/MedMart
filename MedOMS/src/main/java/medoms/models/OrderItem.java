package medoms.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name="OrderItem")
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull
	private long itemId;
	@OneToOne(fetch=FetchType.EAGER)
	private Order order;
	@NotNull
	private int quantity;
	@NotNull
	private double price;
	private double itemDiscount;
	private String name;
	private String imageUrl;
	
	public OrderItem() {
		
	}

	public OrderItem(long itemId, Order order, int quantity, double price, double itemDiscount, String name,
			String imageUrl) {
		this.itemId = itemId;
		this.order = order;
		this.quantity = quantity;
		this.price = price;
		this.itemDiscount = itemDiscount;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
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

	public double getItemDiscount() {
		return itemDiscount;
	}

	public void setItemDiscount(double itemDiscount) {
		this.itemDiscount = itemDiscount;
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
		return "OrederItem [id=" + id + ", itemId=" + itemId + ", order=" + order + ", quantity=" + quantity
				+ ", price=" + price + ", itemDiscount=" + itemDiscount + ", name=" + name + ", imageUrl=" + imageUrl
				+ "]";
	}
	
	
	
	
	
	

}
