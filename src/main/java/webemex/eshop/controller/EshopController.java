package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webemex.eshop.model.*;
import webemex.eshop.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    OrderService orderService;

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


    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public String getUser(Model model) {
        AppUser appUser = appUserService.getAuthenticatedUser();
        model.addAttribute("appUser", appUser);
        return "user";
    }

    @GetMapping("/edit-user")
    @PreAuthorize("isAuthenticated()")
    public String editUser(Model model) {
        AppUser appUser = appUserService.getAuthenticatedUser();
        model.addAttribute("appUser", appUser);
        return "edit-user";
    }

//    Item
    @GetMapping("/create-item")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String createItem(Model model) {
        Item item = new Item();
        model.addAttribute("item", item);
        return "create-item";
    }

    @PostMapping("/save-item")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String saveItem(@ModelAttribute("item") Item item) {
        itemService.saveItem(item);
        return "redirect:/admin-edit-database";
    }

    @GetMapping("/edit-item/{idItem}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String editItem(@PathVariable Long idItem, Model model) {
        Item item = itemService.findItemById(idItem);
        model.addAttribute("item", item);
        return "edit-item";
    }

    @GetMapping("/remove-item/{idItem}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void removeItem(@PathVariable Long idItem) {
        itemService.deleteItemById(idItem);
    }

//    Eshop
    @GetMapping("/eshop")
    @PreAuthorize("isAuthenticated()")
    public String eshop(Model model) {
        model.addAttribute("allItems", itemService.findAllItems());
        return "eshop";
    }

    @PostMapping("/addToCart/{idItem}")
    @PreAuthorize("isAuthenticated()")
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

    @PostMapping("/editCart/{idCartItem}")
    @PreAuthorize("isAuthenticated()")
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

    @GetMapping("/place-order")
    @PreAuthorize("isAuthenticated()")
    public String placeOrder() {
        AppUser appUser = appUserService.getAuthenticatedUser();
        LocalDateTime dateTime = LocalDateTime.now();

//        Create a new order
        Order order = new Order();
        order.setDateTime(dateTime);
        order.setAppUser(appUser);
        orderService.saveOrder(order);

        double totalPrice = 0;

//        Move items from cart to order
        List<CartItem> allCartItems = cartItemService.findAllCartItems();

        for (CartItem cartItem : allCartItems) {
            if (cartItem.getAppUser() == appUser) {
//                Create a new orderItem
                OrderItem orderItem = new OrderItem(cartItem);
                orderItem.setOrder(order);
                orderItemService.saveOrderItem(orderItem);

//                Sum up total order price
                totalPrice += cartItem.getItem().getPrice() * cartItem.getVolume();

//                Delete the item from cart
                cartItemService.deleteItemById(cartItem.getId());
            }
        }

//        Save order
        order.setTotalPrice(totalPrice);
        orderService.saveOrder(order);

        return "order-placed";
    }

    @GetMapping("/my-orders")
    @PreAuthorize("isAuthenticated()")
    public String showOrders(Model model) {
        AppUser appUser = appUserService.getAuthenticatedUser();

//        Find all user's orders
        List<Order> allOrders = orderService.findAllOrders();
        List<Order> userOrders = new ArrayList<>();
        for (Order order : allOrders) {
            if (order.getAppUser() == appUser) {
                userOrders.add(order);
            }
        }

        model.addAttribute("appUser", appUser);
        model.addAttribute("userOrders" , userOrders);
        return "my-orders";
    }

    @GetMapping("/openOrder/{idUserOrder}")
    @PreAuthorize("isAuthenticated()")
    public String openOrder(@PathVariable Long idUserOrder, Model model) {
        Order order = orderService.findOrderById(idUserOrder);
        model.addAttribute("order", order);
        return "order";
    }

//    Admin - edit database
    @GetMapping("/admin-edit-database")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminEditDatabase(Model model) {
        model.addAttribute("allItems", itemService.findAllItems());
        return "admin-edit-database";
    }
}
