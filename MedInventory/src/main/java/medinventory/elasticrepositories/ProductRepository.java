package medinventory.elasticrepositories;



import java.util.List;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import medinventory.models.ProductCatalogue;

public interface ProductRepository extends ElasticsearchRepository<ProductCatalogue, String>{
	
	
	Iterable<ProductCatalogue> findByProductName(String keyword);
	
	@Query("{\"fuzzy\" : {\"productname\": \"?0\"}}")
	Iterable<ProductCatalogue> findProductNameCustom(String keyword);
}
