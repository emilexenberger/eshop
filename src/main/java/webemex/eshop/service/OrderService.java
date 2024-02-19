package webemex.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webemex.eshop.model.Order;
import webemex.eshop.model.OrderItem;
import webemex.eshop.repository.OrderItemRepository;
import webemex.eshop.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Order findOrderById(UUID id) {
        return orderRepository.findById(id).get();
    }

    public void deleteOrderById(UUID id) {
        orderRepository.deleteById(id);
    }
}
