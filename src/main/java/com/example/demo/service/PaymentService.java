package com.example.demo.service;

import com.example.demo.dto.PaymentDTO;
import com.example.demo.entity.Payment;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    public PaymentService(PaymentRepository paymentRepository) { this.paymentRepository = paymentRepository; }

    private static PaymentDTO toDTO(Payment p) {
        return new PaymentDTO(p.getId(), p.getOrderId(), p.getStatus());
    }

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll()
            .stream().map(PaymentService::toDTO).collect(Collectors.toList());
    }

    public PaymentDTO createPayment(PaymentDTO paymentDto) {
        Payment payment = new Payment();
        payment.setOrderId(paymentDto.getOrderId());
        payment.setStatus(paymentDto.getStatus());
        Payment savedPayment = paymentRepository.save(payment);
        return toDTO(savedPayment);
    }

    public PaymentDTO getPaymentById(Long id) {
        Payment p = paymentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id " + id));
        return toDTO(p);
    }
}