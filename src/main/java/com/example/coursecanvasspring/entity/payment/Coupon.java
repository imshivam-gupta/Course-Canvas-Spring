package com.example.coursecanvasspring.entity.payment;

import com.example.coursecanvasspring.enums.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.example.coursecanvasspring.constants.StringConstants.COUPON_COLLECTION;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = COUPON_COLLECTION)
public class Coupon {
    private String _id;
    private String couponCode;
    private Double discount;
    private Double expiryTime;
    private DiscountType discountType;
    private Boolean active;
}
