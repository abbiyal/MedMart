package medinventory.resources;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import medinventory.models.Inventory;
import medinventory.services.MedInventoryService;

@RestController
@Transactional
@RequestMapping("/inventory")
public class InventoryController {
	
	@Autowired
	private MedInventoryService inventoryService;
	
	
	@RequestMapping(value="/addInventory",method=RequestMethod.POST)
	public String addInventory(@RequestBody Inventory record){
		inventoryService.addRecord(record);
		return "Success";
	}
	
	@RequestMapping(value="/getKey",method=RequestMethod.POST)
	public String getKey(@RequestBody Inventory record){
		record.setId();
		return record.getId();
	}
	
	
	@RequestMapping(value="/{id}/inc/{quantity}",method=RequestMethod.GET)
	public String increaseQuantity(@PathVariable("id") String id, @PathVariable("quantity") int quantity){
		return inventoryService.updateRecord(id, quantity);
	}
	
	@RequestMapping(value="/{id}/dec/{quantity}",method=RequestMethod.GET)
	public String decreaseQuantity(@PathVariable("id") String id, @PathVariable("quantity") int quantity){
		return inventoryService.updateRecord(id, 0-quantity);
	}
	
	@RequestMapping(value="/deleteInventory/{id}",method=RequestMethod.GET)
	public boolean deleteRecord(@PathVariable("id") String id){
		return inventoryService.deleteRecord(id);
	}
}
