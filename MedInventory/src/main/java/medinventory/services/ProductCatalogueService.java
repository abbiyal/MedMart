package medinventory.services;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medinventory.elasticrepositories.ProductRepository;
import medinventory.jparepositories.ProductCatalogueJpaRepository;
import medinventory.models.ProductCatalogue;

@Service
public class ProductCatalogueService {
	
	@Autowired
	private ProductCatalogueJpaRepository productCatalogueJpaRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	public String registerNewProduct(ProductCatalogue product) {
		product.setProductId();
		try {
			productCatalogueJpaRepository.save(product);
			productRepository.save(product);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return product.getProductId();
	}
	
	public Optional<ProductCatalogue> getProductInfo(String productId) {
		return productCatalogueJpaRepository.findById(productId);
		
	}
}
