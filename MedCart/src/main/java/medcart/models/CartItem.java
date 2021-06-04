package medcart.models;

public class CartItem {
	
	private String productId;
	private Double price;
	private int quantity;
	
	
	
	public CartItem() {
		
	}

	public CartItem(String productId, Double price, int quantity) {
		this.productId = productId;
		this.price = price;
		this.quantity = quantity;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "ItemResponse [productId=" + productId + ", price=" + price + ", quantity=" + quantity + "]";
	}
	 
	
	

}
