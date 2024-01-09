package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webemex.eshop.model.AppUser;
import webemex.eshop.model.Item;
import webemex.eshop.service.AppUserService;
import webemex.eshop.service.ItemService;

import java.util.ArrayList;

@Controller
public class EshopController {
    @Autowired
    ItemService itemService;

    @Autowired
    AppUserService appUserService;

    @GetMapping("/")
    public String index(Model model) {
        AppUser appUser = appUserService.getAuthenticatedUser();
        model.addAttribute("userLogged", appUser != null);
        model.addAttribute("userAdmin", appUser != null && appUser.getUsername().equals("admin"));
        return "index";
    }

//    User
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/create-user")
    public String createAccount(Model model) {
        AppUser appUser = new AppUser();
        model.addAttribute("appUser", appUser);
        return "create-user";
    }

    @PostMapping("/save-created-user")
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

    @PostMapping("/save-edited-user")
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
    public String showUserEdited() {
        return "user-edited";
    }


    @GetMapping("/user")
    public String getUser(Model model) {
        AppUser appUser = appUserService.getAuthenticatedUser();
        model.addAttribute("appUser", appUser);
        return "user";
    }

    @GetMapping("/edit-user")
    public String editUser(Model model) {
        AppUser appUser = appUserService.getAuthenticatedUser();
        model.addAttribute("appUser", appUser);
        return "edit-user";
    }

//    Item
    @GetMapping("/create-item")
    public String createItem(Model model) {
        Item item = new Item();
        model.addAttribute("item", item);
        return "create-item";
    }

    @PostMapping("/save-item")
    public String saveItem(@ModelAttribute("item") Item item) {
        itemService.saveItem(item);
        return "redirect:/admin-edit-database";
    }

    @GetMapping("/edit-item/{id}")
    public String editItem(@PathVariable Long id, Model model) {
        Item item = itemService.findItemById(id);
        model.addAttribute("item", item);
        return "edit-item";
    }

    @GetMapping("/remove-item/{id}")
    public void removeItem(@PathVariable Long id) {
        itemService.deleteItemById(id);
    }

//    Eshop
    @GetMapping("/eshop")
    public String eshop(Model model) {
        model.addAttribute("allItems", itemService.findAllItems());
        return "eshop";
    }

//    Admin - edit database
    @GetMapping("/admin-edit-database")
    public String adminEshop(Model model) {
        model.addAttribute("allItems", itemService.findAllItems());
        return "admin-edit-database";
    }


}
