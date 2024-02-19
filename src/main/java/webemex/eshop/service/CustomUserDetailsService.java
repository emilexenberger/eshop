package webemex.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import webemex.eshop.model.AppUser;
import webemex.eshop.repository.UserRepository;

// TODO: Tu sme tento subor premiestnili zo Security zlozky. Tato metoda by sa mala dat presunut samostatne do AppUserService. Nastudovat, ako by sa to dalo.
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        TODO: Nastudovat Java optional a aplikovat ho tu
        AppUser appUser = userRepository.findByUsername(username);
//        AppUser appUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        if (appUser == null) {
            throw new UsernameNotFoundException(username);
        }
        UserDetails user = User.withUsername(appUser.getUsername())
                               .password(appUser.getPassword())
                               .authorities(appUser.getRole())
                               .build();
        return user;
    }
}
