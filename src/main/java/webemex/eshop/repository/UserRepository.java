package webemex.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webemex.eshop.model.AppUser;

import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, UUID> {
    AppUser findByUsername(String username);
}
