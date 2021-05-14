package medinventory.elasticrepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import medinventory.models.Inventory;

public interface InventoryElasticRepository extends ElasticsearchRepository<Inventory, String>{

	Iterable<Inventory> findByShopId(Long shopId);
	Iterable<Inventory> findByProductIdAndQuantityGreaterThan(String productid,int quantity);
	List<Inventory> findByProductIdAndShopId(String productid,Long shopId);
	
	
}
