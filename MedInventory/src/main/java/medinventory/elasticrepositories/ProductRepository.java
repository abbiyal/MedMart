package medinventory.elasticrepositories;



import java.util.List;

import org.json.JSONArray;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;



import medinventory.models.ProductCatalogue;

public interface ProductRepository extends ElasticsearchRepository<ProductCatalogue, String>{
	
	
	Iterable<ProductCatalogue> findByProductName(String keyword);
	
	@Query("{\"bool\": {\"must\": {\"terms\": {\"productId\":?0}}}}")
	List<ProductCatalogue> searchShopProducts(JSONArray keyword);

}
