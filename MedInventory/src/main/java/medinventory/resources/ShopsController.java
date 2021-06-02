package medinventory.resources;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import medinventory.models.Shops;
import medinventory.services.ShopsService;

@RestController
@Transactional
public class ShopsController {
	
	@Autowired
	ShopsService shopsService;
	
	@RequestMapping(value="/addshop",method=RequestMethod.POST)
	public String addShop(@RequestBody Shops shop) {
		System.out.println(shop);
		return shopsService.addShop(shop);	
	}
	
	@RequestMapping(value="/getShop",method=RequestMethod.GET)
	public HashMap<String,String> findShopDetails(@RequestParam(name="shopid") String shopId) {
		return shopsService.findShop(Long.parseLong(shopId));	
	}
	
	
	
}
