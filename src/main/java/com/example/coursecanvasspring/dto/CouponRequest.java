package com.example.coursecanvasspring.dto;

import com.example.coursecanvasspring.enums.DiscountType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponRequest {
    private String couponCode;
    private Double discount;
    private DiscountType discountType;
    private Double expiryTime;
    private Boolean active;
}
