package medcart.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import medcart.models.Cart;
import medcart.models.CartRepository;
import medcart.models.Item;
import medcart.models.ItemRepository;

@Service
public class CartService {
	
//	private RestTemplate restTemplate=new RestTemplate();
	
	@Autowired
	private CartRepository cartRepository;
	
	public String createNewCart(String userId)
	{
		Cart cart=new Cart(userId,"NEW",0.00,0, null);
		cartRepository.save(cart);
		return "success";
	}	
	public Optional<Cart> getCartById(String userId)
	{
		return cartRepository.findCartByUserId(userId);
	}
	
//	public String addCartItem(Cart cart)
//	{
//		
////		cartRepository.save(cart);
//		return "success";
//	}
	
	public String updateCart(Cart cart)
	{
		cartRepository.save(cart);
		return "success";
	}
	public String deleteCart(long cartId)
	{
		cartRepository.deleteById(cartId);
		return "success";
	}

}
