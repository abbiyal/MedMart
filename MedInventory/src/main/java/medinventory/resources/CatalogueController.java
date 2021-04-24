package medinventory.resources;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import medinventory.models.ProductCatalogue;
import medinventory.services.ProductCatalogueService;

@RestController
@Transactional
@RequestMapping("/catalogue")
public class CatalogueController {
	@Autowired
	private ProductCatalogueService catalogueService;
	
	@RequestMapping(value="/{productId}",method=RequestMethod.GET)
	public Optional<ProductCatalogue> getProduct(@PathVariable("productId") String productId){
		Optional<ProductCatalogue> product = catalogueService.getProductInfo(productId);
		return product;
	}
	
	@RequestMapping(value="/addproduct",method=RequestMethod.POST)
	public String setProduct(@RequestBody ProductCatalogue product){
		System.out.println(product.getProductName());
		catalogueService.registerNewProduct(product);
		return "success";
	}
	
	@RequestMapping(value="/getKey",method=RequestMethod.POST)
	public String getKey(@RequestBody ProductCatalogue product){
		product.setProductId();
		return product.getProductId();
	}
	
}
