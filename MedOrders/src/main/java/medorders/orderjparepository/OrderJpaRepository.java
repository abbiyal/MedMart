package medorders.orderjparepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import medorders.models.Orders;


public interface OrderJpaRepository extends CrudRepository<Orders, Long>{

	List<Orders> findByUserId(String userId);

}
