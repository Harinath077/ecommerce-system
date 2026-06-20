package com.ecommerce.payment_service.dto;

import com.ecommerce.payment_service.entity.PaymentStatus;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private Long paymentId;

    private PaymentStatus status;

    private String message;

}
