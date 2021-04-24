package medinventory.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import medinventory.jparepositories.InventoryRepository;
import medinventory.models.Inventory;

@Service
public class MedInventoryService {
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	public String addRecord(Inventory record) {
			record.setId();
			inventoryRepository.save(record);
			return "success";
	}
	
	public String updateRecord(String id, int quantityChange) {
		Optional<Inventory> record = inventoryRepository.findById(id);
		
		if (record.isPresent()) {
			if (record.get().getQuantity()+quantityChange < 0) {
				return "Invalid Quantity";
			}
			else {
				record.get().setQuantity(record.get().getQuantity()+quantityChange);
				inventoryRepository.save(record.get());
				return "Success";
			}
		}
		
		return "id not found";
	}
	
	public boolean deleteRecord(String id) {
		try {
			inventoryRepository.deleteById(id);
		}
		catch(Exception e) {
			return false;
		}
		
		return true;
	}
}
