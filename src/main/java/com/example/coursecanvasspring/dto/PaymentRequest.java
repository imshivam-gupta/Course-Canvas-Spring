package com.example.coursecanvasspring.dto;

import com.example.coursecanvasspring.enums.PaymentMethod;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private PaymentMethod paymentMethod;
    private JsonNode paymentDetails;
}
