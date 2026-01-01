package com.example.demo.controller;

import com.example.demo.dto.OrderDTO;
import com.example.demo.repository.OrderRepository;
import com.example.demo.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void getAllOrders_ReturnsOk() throws Exception {
        Order order = new Order(); order.setUserId(5L); order.setAmount(44.0);
        orderRepository.save(order);

        mockMvc.perform(get("/api/orders"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].amount").value(44.0));
    }

    @Test
    void createOrder_ReturnsCreatedOrder() throws Exception {
        OrderDTO dto = new OrderDTO(null, 7L, 20.5);
        mockMvc.perform(post("/api/orders")
            .content(objectMapper.writeValueAsString(dto))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.userId").value(7L));
    }

    @Test
    void getOrderById_ReturnsOrder() throws Exception {
        Order order = new Order(); order.setUserId(6L); order.setAmount(18.5);
        order = orderRepository.save(order);

        mockMvc.perform(get("/api/orders/" + order.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.amount").value(18.5));
    }
}