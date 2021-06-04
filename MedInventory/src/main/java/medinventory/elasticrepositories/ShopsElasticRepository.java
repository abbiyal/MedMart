package medinventory.elasticrepositories;

import java.util.List;


import org.json.JSONArray;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.annotations.ScriptedField;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import medinventory.models.ProductCatalogue;
import medinventory.models.Shops;

public interface ShopsElasticRepository extends ElasticsearchRepository<Shops, Long>{
	
	
	@Query("{\r\n" + 
			"    \"bool\": {\r\n" + 
			"      \"must\": [\r\n" + 
			"        {\"terms\": {\r\n" + 
			"          \"shopId\": ?0\r\n" + 
			"        }\r\n" + 
			"        }\r\n" + 
			"      ],\r\n" + 
			"      \"filter\": {\r\n" + 
			"        \"geo_distance\": {\r\n" + 
			"          \"distance\": \"15km\",\r\n" + 
			"          \"location\":  ?1  \r\n" + 
			"        }\r\n" + 
			"      }\r\n" + 
			"    }\r\n" + 
			"  }")
	List<Shops> searchShops(JSONArray shopIds,String location);
	
	List<Shops> findShopsByCity(String City);
	
	
	@Query("{\"bool\": {" + 
			"      \"must\": {" + 
			"        \"match_all\": {}" + 
			"      }," + 
			"      \"filter\": {" + 
			"        \"geo_distance\": {" + 
			"          \"distance\": \"15km\"," + 
			"          \"location\": ?0" + 
			"        }" + 
			"      }" + 
			"    }}")
	List<Shops> findNearbyShops(String location);
	
}
