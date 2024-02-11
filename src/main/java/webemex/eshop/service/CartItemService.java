package webemex.eshop.service;

import org.springframework.stereotype.Service;
import webemex.eshop.model.CartItem;
import webemex.eshop.model.Item;
import webemex.eshop.repository.CartItemRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    
    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public void saveItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    public List<CartItem> findAllCartItems() {
        return cartItemRepository.findAll();
    }

    public CartItem findItemById(UUID id) {
        return cartItemRepository.findById(id).get();
    }

    public void deleteItemById(UUID id) {
        cartItemRepository.deleteById(id);
    }
}
