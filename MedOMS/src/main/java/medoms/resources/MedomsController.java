package medoms.resources;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import medoms.models.Item;
import medoms.models.Orders;
import medoms.models.Wrapperorder;

@RestController
@Transactional
@RequestMapping("/orders")
public class MedomsController {
	
	@RequestMapping(value="/placeorder",method=RequestMethod.POST)
	public String placeOrder(@RequestBody Wrapperorder orderitems){
		List<Item> items=orderitems.getItems();
		
		return "";
	}
	
	@RequestMapping(value="/getorders/{UserId}",method=RequestMethod.GET)
	public Optional<Orders> getOrder(@PathVariable("USerId") String userid){
		return;
	}
	
	

}
