package webemex.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webemex.eshop.model.OrderItem;
import webemex.eshop.repository.OrderItemRepository;

import java.util.List;
import java.util.UUID;

@Service
public class OrderItemService {
    @Autowired
    OrderItemRepository orderItemRepository;

    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public List<OrderItem> findAllOrders() {
        return orderItemRepository.findAll();
    }

    public OrderItem findOrderById(UUID id) {
        return orderItemRepository.findById(id).get();
    }

    public void deleteOrderById(UUID id) {
        orderItemRepository.deleteById(id);
    }
}
