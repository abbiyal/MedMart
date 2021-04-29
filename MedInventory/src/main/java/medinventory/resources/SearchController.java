package medinventory.resources;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import medinventory.models.Inventory;
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
		return productsCatalogues;
	}
	
	@RequestMapping("/search/products_in_shop")
	public List<ProductCatalogue> findShopProducts(@RequestParam(name="keyword") String keyword){
		Long shopId=Long.parseLong(keyword);
		List<ProductCatalogue> shopInventoryList = productSearchService.findProductsInShop(shopId);
		return shopInventoryList;
	}
	
	@RequestMapping(value = "/search/nearbyShops",method = RequestMethod.POST)
	public List<Shops> findnearbyshops(@RequestBody HashMap<String,String> request){
		String latitude = request.get("lat");
		String longitude = request.get("long");
		String city = request.get("city");
		
		List<Shops> nearbyShops = productSearchService.findNearbyShops(latitude,longitude,city);
		return nearbyShops;
	}
}
