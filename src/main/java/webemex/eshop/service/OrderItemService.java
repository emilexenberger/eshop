package webemex.eshop.service;

import org.springframework.stereotype.Service;
import webemex.eshop.model.OrderItem;
import webemex.eshop.repository.OrderItemRepository;

import java.util.List;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public List<OrderItem> findAllOrders() {
        return orderItemRepository.findAll();
    }

    public OrderItem findOrderById(Long id) {
        return orderItemRepository.findById(id).get();
    }

    public void deleteOrderById(Long id) {
        orderItemRepository.deleteById(id);
    }
}
