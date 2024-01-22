package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webemex.eshop.model.AppUser;
import webemex.eshop.model.CartItem;
import webemex.eshop.model.Item;
import webemex.eshop.model.OrderItem;
import webemex.eshop.service.AppUserService;
import webemex.eshop.service.CartItemService;
import webemex.eshop.service.ItemService;
import webemex.eshop.service.OrderItemService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EshopController {
    @Autowired
    ItemService itemService;

    @Autowired
    AppUserService appUserService;

    @Autowired
    CartItemService cartItemService;

    private final OrderItemService orderItemService;

    public EshopController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

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

    @GetMapping("/edit-item/{idItem}")
    public String editItem(@PathVariable Long idItem, Model model) {
        Item item = itemService.findItemById(idItem);
        model.addAttribute("item", item);
        return "edit-item";
    }

    @GetMapping("/remove-item/{idItem}")
    public void removeItem(@PathVariable Long idItem) {
        itemService.deleteItemById(idItem);
    }

//    Eshop
    @GetMapping("/eshop")
    public String eshop(Model model) {
        model.addAttribute("allItems", itemService.findAllItems());
        return "eshop";
    }

    @PostMapping("/addToCart/{idItem}")
    public String addToCard(@PathVariable Long idItem, @RequestParam("enteredVolume") int enteredVolume) {
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
        return "redirect:/eshop";
    }

    @GetMapping("/cart")
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

    @PostMapping("/editCart/{idCartItem}")
    public String editCartItem(@PathVariable Long idCartItem, @RequestParam("enteredVolume") int enteredVolume) {
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

    @GetMapping("/place-order")
    public String placeOrder() {
        AppUser appUser = appUserService.getAuthenticatedUser();

        // Move items from cart to order
        LocalDateTime dateTime = LocalDateTime.now();
        List<CartItem> allCartItems = cartItemService.findAllCartItems();
        for (CartItem cartItem : allCartItems) {
            if (cartItem.getAppUser() == appUser) {
                cartItemService.deleteItemById(cartItem.getId());
                orderItemService.saveOrder(new OrderItem(cartItem, dateTime));
            }
        }

        return "order-placed";
    }

//    Admin - edit database
    @GetMapping("/admin-edit-database")
    public String adminEditDatabase(Model model) {
        model.addAttribute("allItems", itemService.findAllItems());
        return "admin-edit-database";
    }


}
