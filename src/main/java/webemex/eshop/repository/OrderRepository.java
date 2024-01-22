package webemex.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webemex.eshop.model.Order;
import webemex.eshop.model.OrderItem;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
