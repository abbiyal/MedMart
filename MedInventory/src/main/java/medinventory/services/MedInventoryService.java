package medinventory.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medinventory.elasticrepositories.InventoryElasticRepository;
import medinventory.jparepositories.InventoryRepository;
import medinventory.models.Inventory;
import medinventory.models.ProductCatalogue;

@Service
public class MedInventoryService {
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Autowired
	private InventoryElasticRepository inventoryElasticRepository;
	
	public String addRecord(Inventory record) {
			record.setId();
			System.out.println("in inventory");
			inventoryRepository.save(record);
			inventoryElasticRepository.save(record);
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
				inventoryElasticRepository.save(record.get());
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
	
	public HashMap<String,List<String>> verifyAndDecreaseQuantity(HashMap<String,List<String>> request){
		List<String> productIds = request.get("productIds");
		List<String> quantity = request.get("quantity");
		Long shopId = Long.parseLong(request.get("shopId").get(0));
		
		HashMap<String,String> quantityMap = new HashMap<String,String>();
		
		for(int i=0;i<productIds.size();i++) {
					quantityMap.put(productIds.get(i),quantity.get(i));
		}
		
		HashMap<String,List<String>> response = new HashMap<String,List<String>>();
		List<String> responses = new ArrayList<String>();
		
		JSONArray productIdsJSON = new JSONArray(productIds);
		
		List<Inventory> inventoryIds = inventoryElasticRepository.findByProductIdsAndShopId(shopId,productIdsJSON);
		
		
		List<Inventory> defaulterProducts = new ArrayList<Inventory>();
		
		for(int i=0;i<inventoryIds.size();i++) {
			int requiredQty = Integer.parseInt(quantityMap.get(inventoryIds.get(i).getProductId()));
			if(inventoryIds.get(i).getQuantity() < requiredQty) {
				defaulterProducts.add(inventoryIds.get(i));
			}
		}
		
		if(defaulterProducts.isEmpty()){
			for(int i=0;i<inventoryIds.size();i++) {
				int quantitytoDecrease =Integer.parseInt(quantityMap.get(inventoryIds.get(i).getProductId()));
				updateRecord(inventoryIds.get(i).getId(),0-quantitytoDecrease);
			}
			
		}
		else if(defaulterProducts.size()==1){
			Inventory defaulter = defaulterProducts.get(0);
			String productName = defaulter.getProductname();
			String quantityAvailable = String.valueOf(defaulter.getQuantity());
			responses.add(productName);
			responses.add(quantityAvailable);
		}
		else {
			responses.add("multiple");
		}
		response.put("response", responses);
		return response;
		
	}

	public HashMap<String, String> increaseQuantityOnFailure(HashMap<String, List<String>> request) {
		List<String> productIds = request.get("productIds");
		List<String> quantity = request.get("quantityIds");
		Long shopId = Long.parseLong(request.get("shopId").get(0));
		HashMap<String,String> quantityMap = new HashMap<String,String>();
		HashMap<String,String> response = new HashMap<String,String>();
		for(int i=0;i<productIds.size();i++) {
					quantityMap.put(productIds.get(i),quantity.get(i));
		}
		
		JSONArray productIdsJSON = new JSONArray(productIds);
		List<Inventory> inventoryIds = inventoryElasticRepository.
				findByProductIdsAndShopId(shopId,productIdsJSON);
		
		for(int i=0;i<inventoryIds.size();i++) {
			int quantitytoIncreasae =Integer.parseInt(quantityMap.get(inventoryIds.get(i).getProductId()));
			updateRecord(inventoryIds.get(i).getId(),quantitytoIncreasae);
		}
		response.put("response", "success");
		return response;
	}
}
