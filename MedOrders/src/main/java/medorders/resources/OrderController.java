package medorders.resources;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Order;
import com.razorpay.RazorpayException;

import medorders.models.OrderDetailCard;
import medorders.models.OrderItem;
import medorders.models.Orders;
import medorders.models.PastOrderCard;
import medorders.orderservice.OrderService;

@RestController
@Transactional
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@RequestMapping("/orders/generateOrderId")
	public HashMap<String,String> generateOrderId(@RequestParam(name = "amount") String amount){
		
		Long totalValue = Long.parseLong(amount);
		
		Long receiptId = orderService.addOrder(totalValue);
		String orderId = orderService.generateOrder(totalValue,String.valueOf(receiptId));
		
		HashMap<String,String> response = new HashMap<String,String>();
		response.put("orderid",orderId);
		response.put("receiptid", String.valueOf(receiptId));
		
		System.out.println(orderId);
		return response;
	}
	
	@RequestMapping(value="/orders/success",method=RequestMethod.POST)
	public HashMap<String,String> addOrderAfterSuccess(@RequestBody HashMap<String,List<String>> request){
		return orderService.addOrderAfterSuccess(request);
	}

	@RequestMapping(value="/orders/failure",method=RequestMethod.GET)
	public HashMap<String,String> failedOrder(@RequestParam(value = "orderId") String orderId){
		return orderService.deleteOrder(orderId);
	}
	
	@RequestMapping("/orders/getorders")
	public List<PastOrderCard> getOrders(@RequestParam(value = "userId") String userId){
		return orderService.getOrder(userId);
	}
	
	@RequestMapping("/orders/orderdetails")
	public List<OrderDetailCard> getOrderDetails(@RequestParam(value = "orderId") String receiptId){
		return orderService.getOrderDerails(receiptId);
	}
	
}
