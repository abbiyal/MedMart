package medinventory.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MatchQuery;

import org.hibernate.Criteria;
import org.json.JSONArray;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import com.mysql.cj.xdevapi.JsonArray;

import medinventory.elasticrepositories.InventoryElasticRepository;
import medinventory.elasticrepositories.ProductRepository;
import medinventory.elasticrepositories.ShopsElasticRepository;
import medinventory.models.Inventory;
import medinventory.models.ProductCatalogue;
import medinventory.models.Shops;

@Service
public class SearchService {

	@Autowired
	ProductRepository productElasticRepository;
	
	@Autowired
	InventoryElasticRepository inventoryElasticRepository;
	
	@Autowired
	ShopsElasticRepository shopElasticRepository;
	
	private Double currLat;
	private Double currLong;
	
	public List<ProductCatalogue> findbyKeyword(String keyword){
		Iterable<ProductCatalogue> products=productElasticRepository.findByProductName(keyword);
		List<ProductCatalogue> productMatches = new ArrayList<ProductCatalogue>();
		products.forEach(productMatches::add);
		return productMatches;
	}
	
	public List<ProductCatalogue> findProductsInShop(Long shopId){
		Iterable<Inventory> inventoryProducts = inventoryElasticRepository.findByShopId(shopId);
		List<String> productIds = new ArrayList<String>();
		inventoryProducts.forEach(new Consumer<Inventory>() {
			
			@Override
            public void accept(Inventory t)
            {
  
                productIds.add(t.getProductId());
            }
		});
		JSONArray productIdsArray = new JSONArray(productIds);
		List<ProductCatalogue> products = productElasticRepository.searchShopProducts(productIdsArray);
		return products;
	}
	
	public List<Shops> findShops(String productId){
		Iterable<Inventory> inventoryProducts = inventoryElasticRepository.findByProductId(productId);
		List<Long> shopIds = new ArrayList<Long>();
		inventoryProducts.forEach(new Consumer<Inventory>() {
			
			@Override
            public void accept(Inventory t)
            {
  
                shopIds.add(t.getShopId());
            }
		});
		JSONArray shopIdsArray = new JSONArray(shopIds);
		List<Shops> shops = shopElasticRepository.searchShops(shopIdsArray);
		return shops;
	}
	
	public double distance(double fromLat, double fromLon, double toLat, double toLon) {
        double radius = 6378137;   // approximate Earth radius, *in meters*
        double deltaLat = toLat - fromLat;
        double deltaLon = toLon - fromLon;
        double angle = 2 * Math.asin( Math.sqrt(
                Math.pow(Math.sin(deltaLat/2), 2) +
                        Math.cos(fromLat) * Math.cos(toLat) *
                                Math.pow(Math.sin(deltaLon/2), 2) ) );
        return radius * angle;
    }
	
	public List<Shops> findNearbyShops(String latitude,String longitude,String city){
		List<Shops> nearbyShops = shopElasticRepository.findShopsByCity(city);
		currLat=Double.parseDouble(latitude);
		currLong=Double.parseDouble(longitude);
		Comparator<Shops> compareById = new Comparator<Shops>() {
		    @Override
		    public int compare(Shops s1, Shops s2) {
		        double lat1=s1.getLatitude();
		        double long1=s1.getLongitude();
		        double lat2=s2.getLatitude();
		        double long2=s2.getLongitude();
		        
		        double distanceToPlace1 = distance(currLat, currLong, lat1, long1);
		        double distanceToPlace2 = distance(currLat, currLong, lat2, long2);
		        return (int) (distanceToPlace1 - distanceToPlace2);
		      
		    }
		};
		Collections.sort(nearbyShops, compareById);
		return nearbyShops;
	}
	
}
