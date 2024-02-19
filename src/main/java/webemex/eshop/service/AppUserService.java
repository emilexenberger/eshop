package webemex.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import webemex.eshop.model.AppUser;
import webemex.eshop.model.Item;
import webemex.eshop.repository.UserRepository;

import java.util.List;

@Service
public class AppUserService {
    @Autowired
    UserRepository userRepository;

    public void saveAppUser(AppUser appUser) {
        userRepository.save(appUser);
    }

    public AppUser getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        AppUser appUser = userRepository.findByUsername(username);
//        AppUser appUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return appUser;
    }

    public List<AppUser> findAllUsers() {
        return userRepository.findAll();
    }
}
