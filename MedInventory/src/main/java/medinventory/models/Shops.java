package medinventory.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

@Entity
@Document(indexName = "shops")
public class Shops {

	@Id
	@org.springframework.data.annotation.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long shopId;
	private Long vendorId;
	private String address;
	private String city;
	@GeoPointField
	private String location;
	String shopName;
	
	
	public Shops() {
		super();
	}

	
	
	public Shops(Long shopId, Long vendorId, String address, String city, String location, String shopName) {
		super();
		this.shopId = shopId;
		this.vendorId = vendorId;
		this.address = address;
		this.city = city;
		this.location = location;
		this.shopName = shopName;
	}



	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getVendorId() {
		return vendorId;
	}
	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	
	
	
}
