package medorders.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Orders")
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long receiptId;
	
	private String razorPayOrder;
	private Long shopId;
	private String dateTimeOfOrder;
	private String status;
	private Long totalValue;
	private String deliveryAddress;
	private String userId;
	private String shopName;
	private String shopAddress;
	
	
	public Orders() {
	}

	

	public Orders(String razorPayOrder, Long shopId, String dateTimeOfOrder, String status, Long totalValue,
			String deliveryAddress, String userId, String shopName, String shopAddress) {
		super();
		this.razorPayOrder = razorPayOrder;
		this.shopId = shopId;
		this.dateTimeOfOrder = dateTimeOfOrder;
		this.status = status;
		this.totalValue = totalValue;
		this.deliveryAddress = deliveryAddress;
		this.userId = userId;
		this.shopName = shopName;
		this.shopAddress = shopAddress;
	}



	public long getReceiptId() {
		return receiptId;
	}


	public void setReceiptId(long receiptId) {
		this.receiptId = receiptId;
	}


	public String getRazorPayOrder() {
		return razorPayOrder;
	}


	public void setRazorPayOrder(String razorPayOrder) {
		this.razorPayOrder = razorPayOrder;
	}


	public Long getShopId() {
		return shopId;
	}


	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}


	public String getDateTimeOfOrder() {
		return dateTimeOfOrder;
	}


	public void setDateTimeOfOrder(String dateTimeOfOrder) {
		this.dateTimeOfOrder = dateTimeOfOrder;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Long getTotalValue() {
		return totalValue;
	}


	public void setTotalValue(Long totalValue) {
		this.totalValue = totalValue;
	}


	public String getDeliveryAddress() {
		return deliveryAddress;
	}


	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getShopName() {
		return shopName;
	}


	public void setShopName(String shopName) {
		this.shopName = shopName;
	}


	public String getShopAddress() {
		return shopAddress;
	}


	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	

}
