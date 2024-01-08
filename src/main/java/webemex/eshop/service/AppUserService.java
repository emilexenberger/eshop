package webemex.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webemex.eshop.model.AppUser;
import webemex.eshop.repository.UserRepository;

@Service
public class AppUserService {
    @Autowired
    UserRepository userRepository;

    public void saveAppUser(AppUser appUser) {
        userRepository.save(appUser);
    }
}
