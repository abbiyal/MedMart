package productcatalogue.services;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import productcatalogue.models.CatalogueRepository;
import productcatalogue.models.ProductCatalogue;

@Service
public class ProductCatalogueService {
	
	@Autowired
	private CatalogueRepository catalogueRepository;
	
	public String registerNewProduct(String productName, String doseStrength, String size, String companyName) {
		ProductCatalogue product = new ProductCatalogue(productName, doseStrength, size, companyName);
		catalogueRepository.save(product);
		return product.getProductId();
	}
	
	public Optional<ProductCatalogue> getProductInfo(String productId) {
		return catalogueRepository.findById(productId);
	}
}
