package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import webemex.eshop.model.AppUser;
import webemex.eshop.service.AppUserService;

@Controller
public class IndexController {
    @Autowired
    AppUserService appUserService;

    @GetMapping("/")
    public String index(Model model) {
        AppUser appUser = appUserService.getAuthenticatedUser();
        if (appUser != null) {
            model.addAttribute("appUser", appUser);
        }
        model.addAttribute("userLogged", appUser != null);
        model.addAttribute("roleAdmin", appUser != null && appUser.getRole().equals("ROLE_ADMIN"));
        return "index";
    }
}
