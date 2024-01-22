package webemex.eshop.service;

import org.springframework.stereotype.Service;
import webemex.eshop.model.Order;
import webemex.eshop.model.OrderItem;
import webemex.eshop.repository.OrderItemRepository;
import webemex.eshop.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Order findOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }
}
