package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import webemex.eshop.model.Item;
import webemex.eshop.service.ItemService;

@Controller
public class EshopController {
    @Autowired
    ItemService itemService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/create-item")
    public String createItem(Model model) {
        Item item = new Item();
        model.addAttribute("item", item);
        return "create-item";
    }

    @PostMapping("/save-item")
    public String saveItem(@ModelAttribute("item") Item item) {
        itemService.saveItem(item);
        return "item-saved";
    }

    @GetMapping("/eshop")
    public String eshop(Model model) {
        model.addAttribute("allItems", itemService.findAllItems());
        return "eshop";
    }

    @GetMapping("/admin-eshop")
    public String adminEshop(Model model) {
        model.addAttribute("allItems", itemService.findAllItems());
        return "admin-eshop";
    }

    @GetMapping("/edit-item/{id}")
    public String editItem(@PathVariable Long id, Model model) {
        Item item = itemService.findItemById(id);
        model.addAttribute("item", item);
        return "edit-item";
    }

    @GetMapping("/remove-item/{id}")
    public void removeItem(@PathVariable Long id, Model model) {
        itemService.deleteItemById(id);
    }
}
