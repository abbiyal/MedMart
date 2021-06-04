package medinventory.jparepositories;

import org.springframework.data.repository.CrudRepository;

import medinventory.models.Inventory;

public interface InventoryRepository extends CrudRepository<Inventory, String>{

}
