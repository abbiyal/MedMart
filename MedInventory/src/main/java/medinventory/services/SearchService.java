package medinventory.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
import org.elasticsearch.search.SearchHit;
import org.hibernate.Criteria;
import org.json.JSONArray;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
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
import medinventory.mapsapi.DistanceTime;
import medinventory.models.Inventory;
import medinventory.models.NearbyShopResponse;
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

	
	public List<ProductCatalogue> findbyKeyword(String keyword){
		Iterable<ProductCatalogue> products=productElasticRepository.findByProductName(keyword);
		List<ProductCatalogue> productMatches = new ArrayList<ProductCatalogue>();
		products.forEach(productMatches::add);
		return productMatches;
	}
	
	public List<HashMap<String,String>> findProductsInShop(Long shopId){
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
		ArrayList response = new ArrayList<HashMap<String,String>>();
		List<ProductCatalogue> products = productElasticRepository.searchShopProducts(productIdsArray);
		for(int i = 0;i<products.size();i++) {
			HashMap<String,String> map = new HashMap<String,String>();
			String productId = products.get(i).getProductId();
			Iterable<Inventory> product = inventoryElasticRepository
					.findByProductIdAndQuantityGreaterThan(productId,1);
			
			product.forEach(new Consumer<Inventory>() {
			
			@Override
            public void accept(Inventory t)
            {
  
				map.put("price", String.valueOf((t.getPrice())));
            }
		});
			map.put("id", products.get(i).getProductId());
			map.put("companyName", products.get(i).getCompanyName());
			map.put("doseStrength", products.get(i).getDoseStrength());
			map.put("productName", products.get(i).getProductName());
			map.put("size", products.get(i).getSize());
			map.put("type", products.get(i).getType());
			
			response.add(map);
		}
		return response;
	}
	
	public List<NearbyShopResponse> getShopsWithDistance(String source,List<Shops> shops){
		DistanceTime distancerequest = new DistanceTime();
		List<NearbyShopResponse> nearbytopShops = new ArrayList<NearbyShopResponse>();
		for(int i=0;i<shops.size();i++) {
			String destination = shops.get(i).getLocation();
			String responseJson = "";
			try {
				responseJson = distancerequest.calculate(source,destination);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Shops currShop = shops.get(i);
			NearbyShopResponse obj = new NearbyShopResponse(currShop.getShopName(),
					currShop.getShopId(),responseJson, "",currShop.getAddress());
			nearbytopShops.add(obj);
		}
		return nearbytopShops;
	}
	
	Comparator<NearbyShopResponse> compareByDistance = new Comparator<NearbyShopResponse>() {
	   

		@Override
		public int compare(NearbyShopResponse o1, NearbyShopResponse o2) {
			// TODO Auto-generated method stub
			Double distance1=Double.parseDouble(o1.getDistance().substring(0, o1.getDistance().length()-2));
			Double distance2=Double.parseDouble(o2.getDistance().substring(0, o2.getDistance().length()-2));
			if(distance1>=distance2) {
				return 1;
			}
			return -1;
		}
	};
	
	public List<NearbyShopResponse> findShopsHavingProducts(String productId,String location){
		Iterable<Inventory> inventoryProducts = inventoryElasticRepository.findByProductIdAndQuantityGreaterThan(productId,1);
		List<Long> shopIds = new ArrayList<Long>();
		inventoryProducts.forEach(new Consumer<Inventory>() {
			@Override
            public void accept(Inventory t)
            {
                shopIds.add(t.getShopId());
            }
		});
		System.out.println("got shops");
		JSONArray shopIdsArray = new JSONArray(shopIds);
		List<Shops> shops = shopElasticRepository.searchShops(shopIdsArray,location);
		shops.subList(0, Math.min(25,shops.size()));
		List<NearbyShopResponse> topMatchingShops = getShopsWithDistance(location,shops);
		Collections.sort(topMatchingShops, compareByDistance);
		for(int i=0;i<topMatchingShops.size();i++) {
			Long shopid = topMatchingShops.get(i).getShopId();
			List<Inventory> inv = inventoryElasticRepository.findByProductIdAndShopId(productId, shopid);
			Double price = 50000000.00;
			for(int j=0;j<inv.size();j++) {
				price = Math.min(inv.get(j).getPrice(), price);
				
			}
			topMatchingShops.get(i).setPrice(String.valueOf(price));
		}
		return topMatchingShops;
	}
	
	public List<NearbyShopResponse> findNearbyShops(String location){
		String source = location;
		List<Shops> nearbyShops = shopElasticRepository.findNearbyShops(location);
		List<Shops> topShops = nearbyShops.subList(0, Math.min(10,nearbyShops.size()));
		List<NearbyShopResponse> nearbytopShops = getShopsWithDistance(location,topShops);
		Collections.sort(nearbytopShops,compareByDistance);
		return nearbytopShops;
	}
	
	public List<ProductCatalogue> findProductsWithCategory(String category){

		category = "\"" + category +"\"";
		List<ProductCatalogue> productsOfGivenCategory = productElasticRepository.findByType(category);
		return productsOfGivenCategory;
	}
	
	public List<ProductCatalogue> findProductsWithinCategory(String category,String keyword){
		List<ProductCatalogue> products = productElasticRepository.findByTypeAndProductName(category,keyword);
		return products;
	}

	public List<ProductCatalogue> searchProductsWithinShop(String category,Long shopId,String keyword){
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
		if(category.contentEquals("All")) {
			List<ProductCatalogue> products = productElasticRepository
					.searchShopProductsWithKeyword(productIdsArray,keyword);
			return products;
			
		}
		List<ProductCatalogue> products = productElasticRepository
				.searchShopProductsWithKeywordAndCategory(productIdsArray,keyword,category);
		return products;
		
	}

}
