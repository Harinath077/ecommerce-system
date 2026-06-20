package com.ecommerce.payment_service.service;

import com.ecommerce.payment_service.dto.PaymentRequest;
import com.ecommerce.payment_service.dto.PaymentResponse;
import com.ecommerce.payment_service.entity.Payment;
import com.ecommerce.payment_service.entity.PaymentStatus;
import com.ecommerce.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        log.info("Processing payment for orderId: {} with idempotencyKey: {}",
                request.getOrderId(), request.getIdempotencyKey());

        // 1. Idempotency check - prevent duplicate payments using map() for clean Optional handling
        return paymentRepository.findByIdempotencyKey(request.getIdempotencyKey())
                .map(existingPayment -> {
                    log.info("Payment already processed for idempotencyKey: {}", request.getIdempotencyKey());
                    return PaymentResponse.builder()
                            .paymentId(existingPayment.getId())
                            .status(existingPayment.getStatus())
                            .message("Payment already processed")
                            .build();
                })
                .orElseGet(() -> {
                    // 2. Mock payment gateway - assume always succeeds
                    Payment payment = Payment.builder()
                            .orderId(request.getOrderId())
                            .amount(request.getAmount())
                            .status(PaymentStatus.COMPLETED)
                            .idempotencyKey(request.getIdempotencyKey())
                            .build();

                    // 3. Persist payment record
                    Payment saved = paymentRepository.save(payment);
                    log.info("Payment saved with ID: {} for orderId: {}", saved.getId(), request.getOrderId());

                    // 4. Return response
                    return PaymentResponse.builder()
                            .paymentId(saved.getId())
                            .status(saved.getStatus())
                            .message("Payment processed successfully")
                            .build();
                });
    }
}
