package medcart.resources;

import java.util.Optional;


import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.cj.Session;

//import antlr.collections.List;
import medcart.models.Cart;
import medcart.models.Item;
import medcart.models.CartItem;
import medcart.services.CartService;
import medcart.services.ItemService;

@RestController
@Transactional
@RequestMapping("/carts")
public class cartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	DataSource datasource;
	
	
	public HashMap<String,String> addNewCart(String username){
		Optional<Cart> cart1=cartService.getCartById(username);
		HashMap<String,String> response = new HashMap<String,String>();
		if(cart1.isPresent()) {
			response.put("response","success");
			return response;
		}
		
		String res = cartService.createNewCart(username);
		response.put("response", res);
		return response;
	}
	
	@RequestMapping(value="/getcart",method=RequestMethod.GET)
	public HashMap<String,Object> getCart(@RequestParam(name = "customerId") String customerId){
		System.out.println("hello");
		HashMap<String,String> cartPresent = addNewCart(customerId);
		Optional<Cart> cart1=cartService.getCartById(customerId);
		List<Item> items=itemService.getItemsByCartId(cart1.get().getId());
		HashMap<String,Object>  response = new HashMap<String,Object>();
		response.put("cartId",String.valueOf((cart1.get().getId())));
		response.put("shopId",String.valueOf(cart1.get().getShopId()));
		response.put("totalItems",String.valueOf(cart1.get().getTotalItems()));
		response.put("totalValue",cart1.get().getValue());
		List<Object> itemResponse = new ArrayList<Object>();
		for(int i=0;i<items.size();i++) {
			itemResponse.add(new CartItem(items.get(i).getProductId(),items.get(i).getPrice(),
					items.get(i).getQuantity()));
		}
		response.put("items",itemResponse);
		return response;
	}
	
	@RequestMapping(value="/deleteCart",method=RequestMethod.DELETE)
	public String deleteCart(@RequestParam(name = "userId") String userId) {
		HashMap<String,String> cartPresent = addNewCart(userId);
		long cartId=cartService.getCartById(userId).get().getId();
		itemService.deleteItem(cartId);
		cartService.deleteCart(cartId);
		return "success";
	}
	
	@RequestMapping(value="/deleteIteminCart",method=RequestMethod.DELETE)
	public HashMap<String,String> deleteItemCart(@RequestParam(name = "userId") String userId,
			@RequestParam(name = "productId") String productId)
	{
		HashMap<String,String> cartPresent = addNewCart(userId);
		Cart cart=cartService.getCartById(userId).get();
		long cartId=cart.getId();
		Optional<Item> item=itemService.getItemByItemAndCartId(cartId,productId);
		double itemPrice=item.get().getPrice();
		int itemQuantity=item.get().getQuantity();
		itemService.deleteItemCart(cartId,productId);
		double value=itemPrice*itemQuantity;
		cart.setValue(cart.getValue()-value);
		cart.setTotalItems(cart.getTotalItems()-itemQuantity);
		cartService.updateCart(cart);
		HashMap<String,String> response = new HashMap<String,String>();
		response.put("response","success");
		return response;
	}
	
	@RequestMapping(value="/addIteminCart",method=RequestMethod.POST,
					consumes=MediaType.APPLICATION_JSON_VALUE)
	public HashMap<String,String> addItemCart(@RequestBody HashMap<String,String> request)
	{
		String userId = request.get("username");
		String productId = request.get("productId");
		String quantity = request.get("quantity");
		String price = request.get("price");
		String productName= request.get("productName");
		Long shopId = Long.parseLong(request.get("shopId"));
		HashMap<String,String> response = new HashMap<String,String>();
		HashMap<String,String> cartPresent = addNewCart(userId);
		Item item = new Item(productId,Integer.parseInt(quantity),Double.parseDouble(price),productName);
		System.out.println(item);
		Optional<Cart> cart=cartService.getCartById(userId);
		System.out.println(cart);
		if(!cart.isPresent())
		{
			cartService.createNewCart(userId);
			
		}
		cart=cartService.getCartById(userId);
//		int availableQuantity = 3; //http call;
//				
//		if(Integer.parseInt(quantity)>availableQuantity) {
//			response.put("response","Qty Unavailable");
//		}
		cart.get().setValue(cart.get().getValue()+ item.getPrice()*item.getQuantity());
		cart.get().setTotalItems(cart.get().getTotalItems()+ item.getQuantity());
		cart.get().setShopId(shopId);
		item.setCart(cart.get());
		itemService.addItem(item);
		response.put("response","success");
		return response;
	}
	
	@RequestMapping(value="/updateItemQuantity",method=RequestMethod.POST)
	public HashMap<String,String> updateItemQuantity(@RequestBody HashMap<String,String> request)
	{
		String userId = request.get("username");
		String productId = request.get("productId");
		String quantity = request.get("quantity");
		HashMap<String,String> cartPresent = addNewCart(userId);
		Cart cart=cartService.getCartById(userId).get();
		long cartId=cart.getId();
		Optional<Item> item=itemService.getItemByItemAndCartId(cartId,productId);
		int oldquantity = item.get().getQuantity();
		Double oldPrice = item.get().getPrice()*item.get().getQuantity();
		item.get().setQuantity(Integer.parseInt(quantity));
		Double newPrice = item.get().getPrice()*item.get().getQuantity();
		cart.setValue(cart.getValue()-oldPrice+newPrice);
		cart.setTotalItems(cart.getTotalItems()-oldquantity+item.get().getQuantity());
		itemService.updateItem(item.get());
		cartService.updateCart(cart);
		HashMap<String,String> response = new HashMap<String,String>();
		response.put("response","success");
		return response;
	}
	
	@RequestMapping(value="/emptyCart",method=RequestMethod.GET)
	public HashMap<String,String> emptyCart(@RequestParam(name = "userId") String userId)
	{
		HashMap<String,String> cartPresent = addNewCart(userId);
		Cart cart=cartService.getCartById(userId).get();
		itemService.deleteItem(cart.getId());
		cart.setShopId(null);
		cart.setStatus("NEW");
		cart.setTotalItems(0);
		cart.setValue(0.0);
		cartService.updateCart(cart);
		HashMap<String,String> response = new HashMap<String,String>();
		response.put("response","success");
		return response;
		
	}
	
 
}
