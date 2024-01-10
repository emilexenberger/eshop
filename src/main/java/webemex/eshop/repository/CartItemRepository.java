package webemex.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webemex.eshop.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
