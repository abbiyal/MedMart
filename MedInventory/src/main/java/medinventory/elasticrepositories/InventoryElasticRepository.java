package medinventory.elasticrepositories;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import medinventory.models.Inventory;

public interface InventoryElasticRepository extends ElasticsearchRepository<Inventory, String>{

	Iterable<Inventory> findByShopId(Long shopId);
	Iterable<Inventory> findByProductIdAndShopIdAndQuantityGreaterThan(String productid,Long shopId,int quantity);
	List<Inventory> findByProductIdAndShopId(String productid,Long shopId);
	Iterable<Inventory> findByProductIdAndQuantityGreaterThan(String productid, int quantity);
	
	@Query("{\r\n" + 
			"    \"bool\": {\r\n" + 
			"      \"must\": [\r\n" + 
			"        {\r\n" + 
			"          \"terms\": {\r\n" + 
			"          \"productId\":?1\r\n" + 
			"          }\r\n" + 
			"        },\r\n" + 
			"        {\r\n" + 
			"          \"match\": {\r\n" + 
			"            \"ShopId\":?0 \r\n" + 
			"          }\r\n" + 
			"        }\r\n" + 
			"      ]\r\n" + 
			"    }\r\n" + 
			"    \r\n" + 
			"  }")
	List<Inventory> findByProductIdsAndShopId(Long shopId, JSONArray productsIds);
}
