package medoms.resources;

import javax.persistence.criteria.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import medoms.services.OrderService;

@RestController

public class OmsController {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/")
	public String welcome(){
		System.out.println("hello");
		return "welcome";
	}
	
	
	@RequestMapping(value="/oms/{userId}/NEW",method=RequestMethod.GET)
	public String createNewOrder(@PathVariable("userId") String userId){
		   orderService.createNewOrder(userId);
		   return "Success";
	}

}
