package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webemex.eshop.model.AppUser;
import webemex.eshop.model.CartItem;
import webemex.eshop.model.Item;
import webemex.eshop.service.AppUserService;
import webemex.eshop.service.CartItemService;
import webemex.eshop.service.ItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    AppUserService appUserService;

    @Autowired
    ItemService itemService;

    @Autowired
    CartItemService cartItemService;

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public String showCart(Model model) {
        AppUser appUser = appUserService.getAuthenticatedUser();

//        Find all user cart items
        List<CartItem> allCartItems = cartItemService.findAllCartItems();
        List<CartItem> userCartItems = new ArrayList<>();
        for (CartItem cartItem : allCartItems) {
            if (cartItem.getAppUser() == appUser) {
                userCartItems.add(cartItem);
            }
        }

//        Total price
        double totalPrice = 0;
        for (CartItem userCartItem : userCartItems) {
            totalPrice += userCartItem.getItem().getPrice() * userCartItem.getVolume();
        }

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("userCartItems" , userCartItems);
        return "cart";
    }

    @PostMapping("/add/{idItem}")
    @PreAuthorize("isAuthenticated()")
    public String addToCard(@PathVariable UUID idItem, @RequestParam("enteredVolume") int enteredVolume) {
//        Create empty cartItem
        CartItem cartItem = new CartItem();

//        Assign appUser to cartItem
        AppUser appUser = appUserService.getAuthenticatedUser();
        cartItem.setAppUser(appUser);

//        Subtract volume of item from inventory and assign item to cartItem
        Item item = itemService.findItemById(idItem);
        item.setVolume(item.getVolume() - enteredVolume);
        cartItem.setItem(item);

//        Get list of all userCartItems
        List<CartItem> allCartItems = cartItemService.findAllCartItems();
        List<CartItem> userCartItems = new ArrayList<>();
        for (CartItem existingCartItem : allCartItems) {
            if (existingCartItem.getAppUser() == appUser) {
                userCartItems.add(existingCartItem);
            }
        }

//        If the item already is in the cart, just add up to entered volume, otherwise create a new entry for CartItem table in the database
        for (CartItem existingUserCartItem : userCartItems) {
            if (existingUserCartItem.getItem() == item) {
                System.out.println("Existuje zaznam s tymto itemom pre tohto pouzivatela");
                System.out.println("ID zaznamu v cart_item tabulke je: " + existingUserCartItem.getId());
                existingUserCartItem.setVolume(existingUserCartItem.getVolume() + enteredVolume);
                cartItemService.saveItem(existingUserCartItem);
                return "redirect:/eshop";
            }
        }
        cartItem.setVolume(enteredVolume);
        cartItemService.saveItem(cartItem);
        return "redirect:/eshop/";
    }

    @PostMapping("/edit/{idCartItem}")
    @PreAuthorize("isAuthenticated()")
    public String editCartItem(@PathVariable UUID idCartItem, @RequestParam("enteredVolume") int enteredVolume) {
//        Change volume in the table cart_item
        CartItem cartItem = cartItemService.findItemById(idCartItem);
        int itemDiff = cartItem.getVolume() - enteredVolume;
        if (enteredVolume == 0) {
            cartItemService.deleteItemById(cartItem.getId());
        } else {
            cartItem.setVolume(enteredVolume);
            cartItemService.saveItem(cartItem);
        }

//        Consider the change in volumes and in the table item
        List<Item> allItems = itemService.findAllItems();
        for (Item item : allItems) {
            if (item == cartItem.getItem()) {
                item.setVolume(item.getVolume() + itemDiff);
                itemService.saveItem(item);
            }
        }

        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public String buy(Model model) {
        AppUser appUser = appUserService.getAuthenticatedUser();

//        Find all user cart items
        List<CartItem> allCartItems = cartItemService.findAllCartItems();
        List<CartItem> userCartItems = new ArrayList<>();
        for (CartItem cartItem : allCartItems) {
            if (cartItem.getAppUser() == appUser) {
                userCartItems.add(cartItem);
            }
        }

//        Total price
        double totalPrice = 0;
        for (CartItem userCartItem : userCartItems) {
            totalPrice += userCartItem.getItem().getPrice() * userCartItem.getVolume();
        }

        model.addAttribute("appUser", appUser);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("userCartItems" , userCartItems);
        return "checkout";
    }
}
