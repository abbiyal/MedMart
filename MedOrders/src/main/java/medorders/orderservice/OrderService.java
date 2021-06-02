package medorders.orderservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import medorders.models.OrderDetailCard;
import medorders.models.OrderItem;
import medorders.models.Orders;
import medorders.models.PastOrderCard;
import medorders.orderjparepository.OrderItemJpaRepository;
import medorders.orderjparepository.OrderJpaRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderJpaRepository orderJpaRepository;
	
	@Autowired
	private OrderItemJpaRepository orderItemJpaRepository;
	
	final String KEY_ID = "rzp_test_eutwk0rpzPmlAl";
	final String KEY_SECRET = "MlBiM5nAOs42LrmOtx7lwpZE";
	
	public String generateOrder(Long amount,String receiptId){
		String orderid = "";
		try {
			  RazorpayClient razorpay = new RazorpayClient(KEY_ID, KEY_SECRET);
			  JSONObject orderRequest = new JSONObject();
			  orderRequest.put("amount", amount); // amount in the smallest currency unit
			  orderRequest.put("currency", "INR");
			  orderRequest.put("receipt", receiptId);

			  Order order = razorpay.Orders.create(orderRequest);
			  orderid = order.get("id");
			  
			} catch (RazorpayException e) {
			  // Handle Exception
			  System.out.println(e.getMessage());
			}
		return orderid;
	}
	
	
	public Long addOrder(Long totalValue) {
		Orders order = new Orders();
		Orders orders = orderJpaRepository.save(order);
		return orders.getReceiptId();
	}
	
	public HashMap<String,String> addOrderAfterSuccess(HashMap<String,List<String>> request) {
		Long amount = Long.parseLong(request.get("amount").get(0));
		String razorpayreceiptId = request.get("orderId").get(0);
		Long shopId = Long.parseLong(request.get("shopId").get(0));
		String shopName = request.get("shopName").get(0);
		String shopAddress = request.get("shopAddress").get(0);
		String dateTimeOfOrder = request.get("dateTime").get(0);
		String status = "Pending";
		String deliveryAddress = request.get("deliveryAddress").get(0);
		String userId = request.get("userId").get(0);
		Orders order = new Orders(razorpayreceiptId, shopId, 
				dateTimeOfOrder, status, amount, deliveryAddress, userId,shopName,shopAddress);
		order.setReceiptId(Long.parseLong(request.get("receiptId").get(0)));
		orderJpaRepository.save(order);
		List<String> productIds = request.get("productIds");
		List<String> quantities = request.get("quantityIds");
		List<String> productNames = request.get("nameIds");
		List<String> prices = request.get("priceIds");
		
		for(int i=0;i<productIds.size();i++) {
			String productId = productIds.get(i);
			int quantity = Integer.parseInt(quantities.get(i));
			String productName = productNames.get(i);
			Double price = Double.parseDouble(prices.get(i));
			OrderItem item = new OrderItem(order,productId,productName,price,quantity);
			orderItemJpaRepository.save(item);			
		}
		HashMap<String,String> response = new HashMap<String,String>();
		response.put("response","success");
		return response;
	}


	
	public HashMap<String, String> deleteOrder(String orderId) {
		// TODO Auto-generated method stub
		Long orderid = Long.parseLong(orderId);
		orderItemJpaRepository.deleteOrderItemByOrderReceiptId(orderid);
		orderJpaRepository.deleteById(orderid);
		HashMap<String,String> response = new HashMap<String,String>();
		response.put("response","success");
		return response;
	}

	public List<PastOrderCard> getOrder(String userId){
		List<Orders> order = orderJpaRepository.findByUserId(userId);
		List<PastOrderCard> response = new ArrayList<PastOrderCard>();
		for(int i=0;i<order.size();i++) {
			String receiptId = String.valueOf(order.get(i).getReceiptId());
			Double amount = (order.get(i).getTotalValue())/100.00;
			String dateTime = order.get(i).getDateTimeOfOrder();
			String status = order.get(i).getStatus();
			String deliveryAddress = order.get(i).getDeliveryAddress();
			String shopAddress = order.get(i).getShopAddress();
			String shopName = order.get(i).getShopName();
			
			PastOrderCard pastOrderCard = new PastOrderCard(receiptId,amount,shopName,shopAddress,status,dateTime,deliveryAddress);
			response.add(pastOrderCard);
			
		}
		Collections.sort(response, new Comparator<PastOrderCard>() {

			@Override
			public int compare(PastOrderCard arg0, PastOrderCard arg1) {
				
				return Integer.parseInt(arg1.getOrderId())-Integer.parseInt(arg0.getOrderId());
			}
			
		});
		return response;
	}


	public List<OrderDetailCard> getOrderDerails(String receiptId) {
		List<OrderItem> orderItems = orderItemJpaRepository.
				findOrderItemByOrderReceiptId(Long.parseLong(receiptId));
		List<OrderDetailCard> response = new ArrayList<OrderDetailCard>();
		for(int i=0;i<orderItems.size();i++) {
			String price= String.valueOf(orderItems.get(i).getPrice());
			String quantity = String.valueOf(orderItems.get(i).getQuantity());
			String productName = orderItems.get(i).getProductName();
			String productId = orderItems.get(i).getProductId();
			OrderDetailCard orderDetails = new OrderDetailCard(productName,price,quantity,productId);
			response.add(orderDetails);
		}
		return response;
	}
}
