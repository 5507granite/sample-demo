package com.example.demo.service;

import com.example.demo.dto.PaymentDTO;
import com.example.demo.entity.Payment;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {
    private PaymentRepository paymentRepository;
    private PaymentService paymentService;

    @BeforeEach
    void setup() {
        paymentRepository = mock(PaymentRepository.class);
        paymentService = new PaymentService(paymentRepository);
    }

    @Test
    void getAllPayments_ReturnsList() {
        Payment p = new Payment(); p.setId(1L); p.setOrderId(1L); p.setStatus("PAID");
        when(paymentRepository.findAll()).thenReturn(Arrays.asList(p));
        List<PaymentDTO> payments = paymentService.getAllPayments();

        assertEquals(1, payments.size());
        assertEquals("PAID", payments.get(0).getStatus());
    }

    @Test
    void getPaymentById_Found() {
        Payment p = new Payment(); p.setId(2L); p.setOrderId(2L); p.setStatus("FAIL");
        when(paymentRepository.findById(2L)).thenReturn(Optional.of(p));
        PaymentDTO dto = paymentService.getPaymentById(2L);

        assertEquals("FAIL", dto.getStatus());
    }

    @Test
    void getPaymentById_NotFound_ThrowsException() {
        when(paymentRepository.findById(3L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> paymentService.getPaymentById(3L));
    }

    @Test
    void createPayment_SavesPayment() {
        PaymentDTO dto = new PaymentDTO(null, 2L, "PENDING");
        Payment saved = new Payment(); saved.setId(20L); saved.setOrderId(2L); saved.setStatus("PENDING");
        when(paymentRepository.save(any(Payment.class))).thenReturn(saved);
        PaymentDTO result = paymentService.createPayment(dto);

        assertEquals("PENDING", result.getStatus());
    }
}