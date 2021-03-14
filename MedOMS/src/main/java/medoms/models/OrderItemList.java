package medoms.models;

import java.util.List;

public class OrderItemList {
	
	private List<Item> orderItemList;

	public OrderItemList() {
		
	}

	
	public OrderItemList(List<Item> orderItemList) {
		this.orderItemList = orderItemList;
	}


	public List<Item> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<Item> orderItemList) {
		this.orderItemList = orderItemList;
	}
	
	

}
