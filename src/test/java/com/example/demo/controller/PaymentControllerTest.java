package com.example.demo.controller;

import com.example.demo.dto.PaymentDTO;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.entity.Payment;
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
class PaymentControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private PaymentRepository paymentRepository;
    @Autowired private ObjectMapper objectMapper;

    @Test
    void getAllPayments_ReturnsOk() throws Exception {
        Payment payment = new Payment(); payment.setOrderId(21L); payment.setStatus("CAPTURED");
        paymentRepository.save(payment);

        mockMvc.perform(get("/api/payments"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].status").value("CAPTURED"));
    }

    @Test
    void createPayment_ReturnsCreatedPayment() throws Exception {
        PaymentDTO dto = new PaymentDTO(null, 99L, "OK");
        mockMvc.perform(post("/api/payments")
            .content(objectMapper.writeValueAsString(dto))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("OK"));
    }

    @Test
    void getPaymentById_ReturnsPayment() throws Exception {
        Payment payment = new Payment(); payment.setOrderId(12L); payment.setStatus("PENDING");
        payment = paymentRepository.save(payment);

        mockMvc.perform(get("/api/payments/" + payment.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("PENDING"));
    }
}