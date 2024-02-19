package webemex.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webemex.eshop.model.AppUser;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<AppUser, UUID> {
    AppUser findByUsername(String username);
//    Optional<AppUser> findByUsername(String username);
}
