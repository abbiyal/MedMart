package medcart.models;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CartRepository  extends CrudRepository<Cart, Long>{
		
	public Optional<Cart> findCartByUserId(String userId);
	
}