package com.example.coursecanvasspring.controller;

import com.example.coursecanvasspring.dto.CouponRequest;
import com.example.coursecanvasspring.dto.OrderRequest;
import com.example.coursecanvasspring.dto.PaymentRequest;
import com.example.coursecanvasspring.entity.payment.Coupon;
import com.example.coursecanvasspring.entity.payment.Order;
import com.example.coursecanvasspring.entity.payment.Transaction;
import com.example.coursecanvasspring.enums.PaymentMethod;
import com.example.coursecanvasspring.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.coursecanvasspring.constants.StringConstants.*;

@RestController
@Slf4j
@RequestMapping
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(CREATE_ORDER_ROUTE)
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.processOrder(orderRequest);
        return ResponseEntity.ok(order);
    }

    @PostMapping(PAY_ORDER_ROUTE)
    public ResponseEntity<?> payOrder(@PathVariable String orderId,@RequestBody PaymentRequest paymentRequest) {
        Transaction transaction = orderService.payOrder(orderId, paymentRequest);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping(CREATE_COUPON_ROUTE)
    public ResponseEntity<?> createCoupon(@RequestBody CouponRequest couponRequest) {
        Coupon savedCoupon = orderService.createCoupon(couponRequest);
        return ResponseEntity.ok(savedCoupon);
    }
}
