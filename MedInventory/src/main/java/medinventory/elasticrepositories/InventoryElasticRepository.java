package medinventory.elasticrepositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import medinventory.models.Inventory;

public interface InventoryElasticRepository extends ElasticsearchRepository<Inventory, String>{

	Iterable<Inventory> findByShopId(Long shopId);
	Iterable<Inventory> findByProductId(String productid);
}
