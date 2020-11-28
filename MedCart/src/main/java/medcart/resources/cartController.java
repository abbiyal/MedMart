package medcart.resources;

import java.util.Optional;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.cj.Session;

//import antlr.collections.List;
import medcart.models.Cart;
import medcart.models.Item;
import medcart.services.CartService;
import medcart.services.ItemService;

@RestController
@Transactional
public class cartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping("/")
	public String welcome() {
		return "welcome";
	}
	
	@RequestMapping(value="/carts/{customerId}",method=RequestMethod.GET)
	public List<Item> getCart(@PathVariable("customerId") String customerId){		
		Optional<Cart> cart1=cartService.getCartById(customerId);
		List<Item> items=itemService.getItemsByCartId(cart1.get().getCartId());
		return items;
	}
	
	@RequestMapping(value="/carts/{customerId}",method=RequestMethod.DELETE)
	public String deleteCart(@PathVariable("customerId") String userId) {
		long cartId=cartService.getCartById(userId).get().getCartId();
		itemService.deleteItem(cartId);
		cartService.deleteCart(cartId);
		return "success";
	}
	
	@RequestMapping(value="/carts/{customerId}/items/{ItemId}/",method=RequestMethod.DELETE)
	public String deleteItemCart(@PathVariable("customerId") String userId,@PathVariable("ItemId") long itemId)
	{
		Cart cart=cartService.getCartById(userId).get();
		long cartId=cart.getCartId();
		Optional<Item> item=itemService.getItemByItemAndCartId(cartId,itemId);
		double itemPrice=item.get().getPrice();
		int itemQuantity=item.get().getQuantity();
		itemService.deleteItemCart(cartId,itemId);
		double value=itemPrice*itemQuantity;
		cart.setValue(cart.getValue()-value);
		cartService.updateCart(cart);
		return "success";
	}
	
	@RequestMapping(value="/carts/{customerId}/items",method=RequestMethod.POST,
					consumes=MediaType.APPLICATION_JSON_VALUE)
	public String addItemCart(@PathVariable("customerId") String userId,
			@RequestBody Item item)
	{
		System.out.println(item);
		Optional<Cart> cart=cartService.getCartById(userId);
		System.out.println(cart);
		if(!cart.isPresent())
		{
			cartService.createNewCart(userId);
			
		}
		cart=cartService.getCartById(userId);
		cart.get().setValue(cart.get().getValue()+ item.getPrice()*item.getQuantity());
		item.setCart(cart.get());
		itemService.addItem(item);
		return "success added item";
	}
	
	
	@RequestMapping(value="/carts/{customerId}/items/{itemId}/INC")
	public List<Item> increaseItemQuantity(@PathVariable("customerId") String userId,@PathVariable("itemId") long itemId)
	{
		Cart cart=cartService.getCartById(userId).get();
		long cartId=cart.getCartId();
		Optional<Item> item=itemService.getItemByItemAndCartId(cartId,itemId);
		item.get().setQuantity(item.get().getQuantity()+1);
		cart.setValue(cart.getValue()+item.get().getPrice());
		itemService.updateItem(item.get());
		cartService.updateCart(cart);
		return itemService.getItemsByCartId(cartId);
	}
	
	@RequestMapping(value="/carts/{customerId}/items/{itemId}/DEC")
	public List<Item> decreaseItemQuantity(@PathVariable("customerId") String userId,@PathVariable("itemId") long itemId)
	{
		Cart cart=cartService.getCartById(userId).get();
		long cartId=cart.getCartId();
		Optional<Item> item=itemService.getItemByItemAndCartId(cartId,itemId);
		if(item.get().getQuantity()-1<=0)
		{
			this.deleteItemCart(userId, itemId);
			return itemService.getItemsByCartId(cartId);
		}
		item.get().setQuantity(item.get().getQuantity()-1);
		cart.setValue(cart.getValue()-item.get().getPrice());
		itemService.updateItem(item.get());
		cartService.updateCart(cart);
		return itemService.getItemsByCartId(cartId);
	}
	
	
	
}
