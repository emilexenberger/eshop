package webemex.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webemex.eshop.model.Order;
import webemex.eshop.model.OrderItem;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
