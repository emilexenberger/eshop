package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webemex.eshop.service.*;

@Controller
@RequestMapping("/eshop")
public class EshopController {
    @Autowired
    ItemService itemService;

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public String eshop(Model model) {
        model.addAttribute("allItems", itemService.findAllItems());
        return "eshop";
    }
}
