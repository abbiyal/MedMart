package medinventory.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import medinventory.models.InventoryRepository;
import medinventory.models.MedInventory;

@Service
public class MedInventoryService {
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	public String addRecord(MedInventory record) {
			record.setId();
			inventoryRepository.save(record);
			return "success";
	}
	
	public String updateRecord(String id, int quantityChange) {
		Optional<MedInventory> record = inventoryRepository.findById(id);
		
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
