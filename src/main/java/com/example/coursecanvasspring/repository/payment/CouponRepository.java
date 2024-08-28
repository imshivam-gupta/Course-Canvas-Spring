package com.example.coursecanvasspring.repository.payment;

import com.example.coursecanvasspring.entity.payment.Coupon;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CouponRepository extends MongoRepository<Coupon,String> {
    Optional<Coupon> findByCouponCode(String couponCode);
}
