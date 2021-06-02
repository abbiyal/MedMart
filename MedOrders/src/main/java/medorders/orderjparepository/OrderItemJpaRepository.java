package medorders.orderjparepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import medorders.models.OrderItem;
import medorders.models.Orders;

public interface OrderItemJpaRepository extends CrudRepository<OrderItem, Long>{

	void deleteOrderItemByOrderReceiptId(Long orderId);

	List<OrderItem> findOrderItemByOrderReceiptId(long receiptId);
	
}
