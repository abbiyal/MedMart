package medinventory.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import medinventory.models.Inventory;
import medinventory.models.NearbyShopRequest;
import medinventory.models.NearbyShopResponse;
import medinventory.models.ProductCatalogue;
import medinventory.models.Shops;
import medinventory.services.SearchService;


@RestController
public class SearchController {
	
	@Autowired
	private SearchService productSearchService;
	
	
	
	@RequestMapping("/search")
	public List<ProductCatalogue> findProducts(@RequestParam(name="keyword") String keyword){
		System.out.println(keyword);
		List<ProductCatalogue> productsCatalogues=productSearchService.findbyKeyword(keyword);
		System.out.println(productsCatalogues);
		return productsCatalogues;
	}
	
	@RequestMapping("/search/products_in_shop")
	public List<HashMap<String,String>> findShopProducts(@RequestParam(name="shopid") String shopid){
		Long shopId=Long.parseLong(shopid);
		System.out.println(shopId);
		List<HashMap<String,String>> shopInventoryList = productSearchService.findProductsInShop(shopId);
		return shopInventoryList;
	}
	
	@RequestMapping(value = "/search/nearbyShops",method = RequestMethod.GET)
	public List<NearbyShopResponse> findnearbyshops(@RequestParam(name="location") String location){
		System.out.println("NEarbySHops");
		List<NearbyShopResponse> nearbyShops = productSearchService.findNearbyShops(location);
		return nearbyShops;
	}
	
	@RequestMapping(value = "/search/shopshavingproducts",method = RequestMethod.POST)
	public List<NearbyShopResponse> findShopsHavingProducts(@RequestBody HashMap<String,String> request){
		String productId = request.get("productid");
		String location = "\"" + request.get("location") + "\"";
		System.out.println("finding shops with products");
		List<NearbyShopResponse> shopsWithProducts = productSearchService.
				findShopsHavingProducts(productId, location);
		return shopsWithProducts;
		
	}
	
	@RequestMapping(value = "/search/category", method = RequestMethod.GET)
	public List<ProductCatalogue> findProductsWithCategory(@RequestParam(name = "category") String category){
		List<ProductCatalogue> productsOfGivenCategory = productSearchService.findProductsWithCategory(category);
		return productsOfGivenCategory;
	}

	@RequestMapping(value = "/search/withincategory", method = RequestMethod.POST)
	public List<ProductCatalogue> findProductsWithinCategory(@RequestBody HashMap<String,String> request){
		String category = request.get("category");
		String keyword = request.get("query");
		List<ProductCatalogue> products = productSearchService.findProductsWithinCategory(category,keyword);
		return products;
		
	}
	
	@RequestMapping(value = "/search/searchWithinShop",method = RequestMethod.POST)
	public List<HashMap<String, String>> searchWithinShop(@RequestBody HashMap<String,String> request){
		String query = request.get("query");
		Long shopId = Long.parseLong(request.get("shopId"));
		String category = request.get("category");
		System.out.println("killer");
		return productSearchService.searchProductsWithinShop(category, shopId, query);
	}
	
	@RequestMapping(value = "/search/findProductById",method = RequestMethod.POST)
	public List<ProductCatalogue> findPrdouctById(@RequestBody List<String> values){
		for (int i = 0; i < values.size(); i++) {
			System.out.println("hello");
			System.out.println(values.get(i));
		}
		JSONArray products = new JSONArray(values);
		
		return productSearchService.findProductById(products);
	}
	
	@RequestMapping(value = "/search/findShopById",method = RequestMethod.GET)
	public HashMap<String,String> findShopById(@RequestParam(value = "shopId") String shopId){
		Long shopIds = Long.parseLong(shopId);
		Optional<Shops> shop = productSearchService.findShopById(shopIds);
		HashMap<String,String> response = new HashMap<String,String>();
		if(!shop.isPresent()) {
			return response;
		}
		response.put("address",shop.get().getAddress());
		response.put("name",shop.get().getShopName());
		return response; 
	}
	
}
