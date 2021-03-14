package medoms.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import medoms.models.Item;
import medoms.models.Order;
import medoms.models.OrderItem;
import medoms.models.OrderItemList;
import medoms.models.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	private RestTemplate restTemplate=new RestTemplate();
	
	public String createNewOrder(String userId)
	{
		String url="http://localhost:8081/carts/"+userId;
		System.out.println(url);
		ResponseEntity<Item[]> orderItems=restTemplate.getForObject(url, Item[].class);
//		OrderItemList result=restTemplate.getForObject(url, OrderItemList.class);
//		List<Item> orderItems=result.getOrderItemList();
//		orderItems.forEach(p->System.out.println(p));
		return "Sucess";
	}
	

	
}
