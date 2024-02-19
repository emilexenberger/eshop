package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import webemex.eshop.model.AppUser;
import webemex.eshop.model.CartItem;
import webemex.eshop.model.Order;
import webemex.eshop.model.OrderItem;
import webemex.eshop.service.AppUserService;
import webemex.eshop.service.CartItemService;
import webemex.eshop.service.OrderItemService;
import webemex.eshop.service.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    AppUserService appUserService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @GetMapping("/place")
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

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public String showOrders(Model model) {
        AppUser appUser = appUserService.getAuthenticatedUser();

//        Find all user's orders
        List<Order> allOrders = orderService.findAllOrders();
        List<Order> userOrders = new ArrayList<>();
//        TODO: Nastuduj Stream a prepis tento kod do streamu
        for (Order order : allOrders) {
            if (order.getAppUser() == appUser) {
                userOrders.add(order);
            }
        }

        model.addAttribute("appUser", appUser);
        model.addAttribute("userOrders" , userOrders);
        return "my-orders";
    }

    @GetMapping("/open/{idUserOrder}")
    @PreAuthorize("isAuthenticated()")
    public String openOrder(@PathVariable UUID idUserOrder, Model model) {
        Order order = orderService.findOrderById(idUserOrder);
        model.addAttribute("order", order);
        return "order";
    }
}
