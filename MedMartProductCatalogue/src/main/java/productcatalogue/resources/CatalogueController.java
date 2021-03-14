package productcatalogue.resources;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import productcatalogue.models.ProductCatalogue;
import productcatalogue.services.ProductCatalogueService;

@RestController
@Transactional
@RequestMapping("/catalogue")
public class CatalogueController {
	@Autowired
	private ProductCatalogueService catalogueService;
	
	@RequestMapping(value="/{productId}",method=RequestMethod.GET)
	public Optional<ProductCatalogue> getProduct(@PathVariable("productId") String productId,@RequestHeader(value="Authorization") String jwt){
		System.out.println(jwt);
		Optional<ProductCatalogue> product = catalogueService.getProductInfo(productId);
		return product;
	}
	
	@RequestMapping(value="/{productName}/{companyName}/{doseStrength}/{size}",method=RequestMethod.GET)
	public String getProduct(@PathVariable("productName") String productName, @PathVariable("companyName") String companyName, @PathVariable("doseStrength") String doseStrength, @PathVariable("size") String size,@RequestHeader(value="Authorization") String jwt){
		System.out.println(jwt);
		return catalogueService.registerNewProduct(productName, doseStrength, size, companyName);
	}
}
