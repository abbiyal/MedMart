package medinventory.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import medinventory.models.ProductCatalogue;
import medinventory.services.ProductCatalogueService;
import medinventory.services.ProductService;

@RestController
public class SearchController {
	
	@Autowired
	private ProductService productSearchService;
	
	@RequestMapping("/search")
	public List<ProductCatalogue> findProducts(@RequestParam(name="keyword") String keyword){
		System.out.println(keyword);
		List<ProductCatalogue> productsCatalogues=productSearchService.findbyKeyword(keyword);
		return productsCatalogues;
	}

}
