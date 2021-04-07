package medinventory.services;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import medinventory.models.InventoryRepository;
import medinventory.models.MedInventory;

@Service
public class MedInventoryService {
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	private String addRecord(MedInventory record) {
			record.setId();
			inventoryRepository.save(record);
			return record.getProductId();
	}
	
}
