package webemex.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webemex.eshop.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
