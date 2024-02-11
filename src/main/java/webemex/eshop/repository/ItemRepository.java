package webemex.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webemex.eshop.model.Item;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
}
