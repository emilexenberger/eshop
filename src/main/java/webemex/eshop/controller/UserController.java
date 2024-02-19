package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webemex.eshop.model.AppUser;
import webemex.eshop.service.AppUserService;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    AppUserService appUserService;

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public String getUser(Model model) {
        AppUser appUser = appUserService.getAuthenticatedUser();
        model.addAttribute("appUser", appUser);
        return "user";
    }

    @GetMapping("/login")
    public String login() {
        return "login-logout";
    }

    @GetMapping("/create")
    public String createAccount(Model model) {
        AppUser appUser = new AppUser();
        model.addAttribute("appUser", appUser);
        return "create-user";
    }

    @PostMapping("/save-created")
    public String saveCreatedAppUser(@ModelAttribute("appUser") AppUser appUser,
                                     @RequestParam String password,
                                     @RequestParam String confirmPassword,
                                     Model model
    ) {
        for (AppUser appUserDB : appUserService.findAllUsers()) {
            if (appUser.getUsername().equals(appUserDB.getUsername())) {
                model.addAttribute("appUser", appUser);
                model.addAttribute("userExists", true);
                return "/create-user";
            }
        }

        if (password.equals("")) {
            model.addAttribute("appUser", appUser);
            model.addAttribute("passwordEmpty", true);
            return "/create-user";
        } else if (!password.equals(confirmPassword)) {
            model.addAttribute("appUser", appUser);
            model.addAttribute("passwordsMismatch", true);
            return "/create-user";
        }

        appUserService.saveAppUser(appUser);
        return "redirect:/user-created";
    }

    @GetMapping("/user-created")
    public String showUserCreated() {
        return "user-created";
    }

    @PostMapping("/save-edited")
    @PreAuthorize("isAuthenticated()")
    public String saveEditedAppUser(@ModelAttribute("appUser") AppUser appUser,
                                    @RequestParam String password,
                                    @RequestParam String confirmPassword,
                                    Model model
    ) {
        if (password.equals("")) {
            model.addAttribute("appUser", appUser);
            model.addAttribute("passwordEmpty", true);
            return "/edit-user";
        } else if (!password.equals(confirmPassword)) {
            model.addAttribute("appUser", appUser);
            model.addAttribute("passwordsMismatch", true);
            return "/edit-user";
        }

        appUserService.saveAppUser(appUser);
        return "redirect:/user-edited";
    }

    @GetMapping("/user-edited")
    @PreAuthorize("isAuthenticated()")
    public String showUserEdited() {
        return "user-edited";
    }

    @GetMapping("/edit-user")
    @PreAuthorize("isAuthenticated()")
    public String editUser(Model model) {
        AppUser appUser = appUserService.getAuthenticatedUser();
        model.addAttribute("appUser", appUser);
        return "edit-user";
    }
}
