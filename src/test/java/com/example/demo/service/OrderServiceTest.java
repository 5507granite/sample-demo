package com.example.demo.service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }

    @Test
    void getAllOrders_ReturnsList() {
        Order o1 = new Order(); o1.setId(1L); o1.setUserId(2L); o1.setAmount(100.0);
        when(orderRepository.findAll()).thenReturn(Arrays.asList(o1));
        List<OrderDTO> orders = orderService.getAllOrders();

        assertEquals(1, orders.size());
        assertEquals(100.0, orders.get(0).getAmount());
    }

    @Test
    void getOrderById_Found() {
        Order o = new Order(); o.setId(1L); o.setUserId(2L); o.setAmount(55.0);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(o));
        OrderDTO dto = orderService.getOrderById(1L);

        assertEquals(55.0, dto.getAmount());
    }

    @Test
    void getOrderById_NotFound_ThrowsException() {
        when(orderRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(2L));
    }

    @Test
    void createOrder_SavesOrder() {
        OrderDTO dto = new OrderDTO(null, 1L, 25.0);
        Order saved = new Order(); saved.setId(15L); saved.setUserId(1L); saved.setAmount(25.0);
        when(orderRepository.save(any(Order.class))).thenReturn(saved);

        OrderDTO result = orderService.createOrder(dto);

        assertEquals(25.0, result.getAmount());
    }
}