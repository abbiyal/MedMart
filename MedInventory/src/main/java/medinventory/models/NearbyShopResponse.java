package medinventory.models;

public class NearbyShopResponse {
	
	String shopName;
	Long shopId;
	String distance;
	String price;
	
	
	public NearbyShopResponse() {
		
	}



	public NearbyShopResponse(String shopName, Long shopId, String distance,String price) {
		this.shopName = shopName;
		this.shopId = shopId;
		this.distance = distance;
		this.price = price;
	}



	public String getShopName() {
		return shopName;
	}



	public void setShopName(String shopName) {
		this.shopName = shopName;
	}



	public Long getShopId() {
		return shopId;
	}



	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}



	public String getDistance() {
		return distance;
	}



	public void setDistance(String distance) {
		this.distance = distance;
	}



	public String getPrice() {
		return price;
	}



	public void setPrice(String price) {
		this.price = price;
	}



	@Override
	public String toString() {
		return "NearbyShopResponse [shopName=" + shopName + ", shopId=" + shopId + ", distance=" + distance + ", price="
				+ price + "]";
	}



	
	
	

}
