package medinventory.models;

public class NearbyShopRequest {
	
	private Double longitude;
	private Double latitude;
	private String city;
	
	
	public NearbyShopRequest() {
		
	}


	public NearbyShopRequest(Double longitude, Double latitude, String city) {
		
		this.longitude = longitude;
		this.latitude = latitude;
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


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	@Override
	public String toString() {
		return "NearbyShopRequest [longitude=" + longitude + ", latitude=" + latitude + ", city=" + city + "]";
	}


	
	
	
	
	

}
