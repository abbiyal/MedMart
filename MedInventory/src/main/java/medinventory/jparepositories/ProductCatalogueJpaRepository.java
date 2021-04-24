package medinventory.jparepositories;

import org.springframework.data.repository.CrudRepository;

import medinventory.models.ProductCatalogue;

public interface ProductCatalogueJpaRepository extends CrudRepository<ProductCatalogue, String>{

}
