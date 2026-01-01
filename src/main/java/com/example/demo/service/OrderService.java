package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) { this.orderRepository = orderRepository; }

    private static OrderDTO toDTO(Order o) {
        return new OrderDTO(o.getId(), o.getUserId(), o.getAmount());
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
            .stream().map(OrderService::toDTO).collect(Collectors.toList());
    }

    public OrderDTO createOrder(OrderDTO orderDto) {
        Order order = new Order();
        order.setUserId(orderDto.getUserId());
        order.setAmount(orderDto.getAmount());
        Order savedOrder = orderRepository.save(order);
        return toDTO(savedOrder);
    }

    public OrderDTO getOrderById(Long id) {
        Order o = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
        return toDTO(o);
    }
}