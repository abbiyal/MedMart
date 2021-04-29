package medinventory.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

@Entity
@Document(indexName = "Shops")
public class Shops {

	@Id
	@org.springframework.data.annotation.Id
	public long shopId;
	public long vendorId;
	public String address;
	public String city;
	public Double longitude;
	public Double latitude;

	public Shops() {
		
	}

	public Shops(long shopId, long vendorId, String address, String city, Double longitude, Double latitude) {
		super();
		this.shopId = shopId;
		this.vendorId = vendorId;
		this.address = address;
		this.city = city;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public long getShopId() {
		return shopId;
	}
	public void setShopId(long shopId) {
		this.shopId = shopId;
	}
	public long getVendorId() {
		return vendorId;
	}
	public void setVendorId(long vendorId) {
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
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	
	
}
