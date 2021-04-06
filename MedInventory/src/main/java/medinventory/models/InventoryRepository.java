package medinventory.models;

import org.springframework.data.repository.CrudRepository;

import medinventory.models.MedInventory;

public interface InventoryRepository extends CrudRepository<MedInventory, String>{

}
