package medcart.models;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends CrudRepository<Item,Long>{
	
	public List<Item> findItemByCartId(long cartId);
	public void deleteItemByCartId(long cartId);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Item i WHERE i.cart.id=:cartId AND i.productId=:productId")
	public void deleteItemCart(@Param("cartId") long cartId,@Param("productId") String productId);
	
	@Query("Select i from Item i Where i.cart.id=:cartId AND i.productId=:productId")
	public Optional<Item> findItemByItemandCartId(@Param("cartId") long cartId,@Param("productId") String productId);
	
	
}
