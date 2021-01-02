package medcart.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medcart.models.Item;
import medcart.models.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	ItemRepository itemRepository;
	
	public String addItem(Item item)
	{
		itemRepository.save(item);
		return "success item added";
	}
	
	public String updateItem(Item item)
	{
		itemRepository.save(item);
		return "updated item";
	}
	
	public String deleteItem(long  cartId)
	{
		itemRepository.deleteItemByCartId(cartId);
		return "item deleted";
	}
	public String deleteItemCart(long cartId,long itemId)
	{
		itemRepository.deleteItemCart(cartId, itemId);
		return "deleted item successfully";
	}
	public Optional<Item> getItemByItemAndCartId(long cartId,long itemId)
	{
		return itemRepository.findItemByItemandCartId(cartId, itemId);
	}

	public List<Item> getItemsByCartId(long cartId) {
		
		return itemRepository.findItemByCartId(cartId);
	}
}
