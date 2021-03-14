package medoms.models;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.NotNull;

@Entity
@Table(name="Orders")
public class Order {
		
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@NotNull
	private String userId;
	private String sessionId;
	private String status;
	private double subTotal;
	private double itemDiscount;
	private double tax;
	private double shipping;
	private String promo;
	private double promoDiscount;
	private double grandTotal;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdAt;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar updatedAt;
	private String address;
	
	public Order() {
		
	}
	public Order(String userId, String sessionId, String status, double subTotal, double itemDiscount, double tax,
			double shipping, String promo, double promoDiscount, double grandTotal, Calendar createdAt,
			Calendar updatedAt, String address) {
		this.userId = userId;
		this.sessionId = sessionId;
		this.status = status;
		this.subTotal = subTotal;
		this.itemDiscount = itemDiscount;
		this.tax = tax;
		this.shipping = shipping;
		this.promo = promo;
		this.promoDiscount = promoDiscount;
		this.grandTotal = grandTotal;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.address = address;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	public double getItemDiscount() {
		return itemDiscount;
	}
	public void setItemDiscount(double itemDiscount) {
		this.itemDiscount = itemDiscount;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getShipping() {
		return shipping;
	}
	public void setShipping(double shipping) {
		this.shipping = shipping;
	}
	public String getPromo() {
		return promo;
	}
	public void setPromo(String promo) {
		this.promo = promo;
	}
	public double getPromoDiscount() {
		return promoDiscount;
	}
	public void setPromoDiscount(double promoDiscount) {
		this.promoDiscount = promoDiscount;
	}
	public double getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}
	public Calendar getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}
	public Calendar getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Calendar updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", sessionId=" + sessionId + ", status=" + status
				+ ", subTotal=" + subTotal + ", itemDiscount=" + itemDiscount + ", tax=" + tax + ", shipping="
				+ shipping + ", promo=" + promo + ", promoDiscount=" + promoDiscount + ", grandTotal=" + grandTotal
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", address=" + address + "]";
	}
	
	


}
