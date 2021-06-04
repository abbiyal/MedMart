package medinventory.elasticrepositories;



import java.util.List;

import org.json.JSONArray;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;

import medinventory.models.ProductCatalogue;

public interface ProductRepository extends ElasticsearchRepository<ProductCatalogue, String>{
	
	
	Iterable<ProductCatalogue> findByProductName(String keyword);
	
	@Query(value="{\"bool\": {\"must\": {\"terms\": {\"productId\":?0}}}}")
	List<ProductCatalogue> searchShopProducts(JSONArray productIds);
	
	
	List<ProductCatalogue> findByType(String category);
	List<ProductCatalogue> findByTypeAndProductName(String category,String keyword);
	
	
	@Query( " {\r\n" + 
			"    \"bool\": {\r\n" + 
			"      \"must\": [\r\n" + 
			"        {\r\n" + 
			"          \"terms\": {\r\n" + 
			"          \"productId\": ?0\r\n" + 
			"          }\r\n" + 
			"        },\r\n" + 
			"        {\r\n" + 
			"          \"match\": {\r\n" + 
			"            \"productname\": ?1 \r\n" + 
			"          }\r\n" + 
			"        }\r\n" + 
			"        \r\n" + 
			"      ]\r\n" + 
			"    }\r\n" + 
			"    \r\n" + 
			"  }\r\n" )
	List<ProductCatalogue> searchShopProductsWithKeyword(JSONArray productsIds,String keyword);
	
	@Query("{\r\n" + 
			"    \"bool\": {\r\n" + 
			"      \"must\": [\r\n" + 
			"        {\r\n" + 
			"          \"terms\": {\r\n" + 
			"          \"productId\": ?0\r\n" + 
			"          }\r\n" + 
			"        },\r\n" + 
			"        {\r\n" + 
			"          \"match\": {\r\n" + 
			"            \"type\": ?2\r\n" + 
			"          }\r\n" + 
			"        },\r\n" + 
			"        {\r\n" + 
			"          \"match\": {\r\n" + 
			"            \"productname\": ?1\r\n" + 
			"          }\r\n" + 
			"        }\r\n" + 
			"        \r\n" + 
			"      ]\r\n" + 
			"    }\r\n" + 
			"    \r\n" + 
			"  }\r\n")
	List<ProductCatalogue> searchShopProductsWithKeywordAndCategory(JSONArray productsIds,
			String keyword,String category);

}
