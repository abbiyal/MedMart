package medinventory.jparepositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import medinventory.models.Shops;

public interface ShopsJpaRepository extends CrudRepository<Shops,Long>{

	Optional<Shops> findByAddress(String address); 
}
