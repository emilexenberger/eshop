package webemex.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webemex.eshop.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
