package medinventory.elasticrepositories;

import java.util.List;

import org.json.JSONArray;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import medinventory.models.ProductCatalogue;
import medinventory.models.Shops;

public interface ShopsElasticRepository extends ElasticsearchRepository<Shops, Long>{
	
	@Query("{\"bool\": {\"must\": {\"terms\": {\"shopId\":?0}}}}")
	List<Shops> searchShops(JSONArray shopIds);
	
	List<Shops> findShopsByCity(String City);

}
