package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webemex.eshop.model.Item;
import webemex.eshop.service.ItemService;

import java.util.UUID;

@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    ItemService itemService;

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminEditDatabase(Model model) {
        model.addAttribute("allItems", itemService.findAllItems());
        return "admin-edit-database";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createItem(Model model) {
        Item item = new Item();
        model.addAttribute("item", item);
        return "create-item";
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String saveItem(@ModelAttribute("item") Item item) {
        itemService.saveItem(item);
        return "redirect:/item/admin";
    }

    @GetMapping("/edit/{idItem}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String editItem(@PathVariable UUID idItem, Model model) {
        Item item = itemService.findItemById(idItem);
        model.addAttribute("item", item);
        return "edit-item";
    }

    @GetMapping("/remove/{idItem}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void removeItem(@PathVariable UUID idItem) {
        itemService.deleteItemById(idItem);
    }
}
