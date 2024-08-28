package com.example.coursecanvasspring.service;

import com.example.coursecanvasspring.dto.CouponRequest;
import com.example.coursecanvasspring.dto.OrderRequest;
import com.example.coursecanvasspring.dto.PaymentRequest;
import com.example.coursecanvasspring.entity.course.Course;
import com.example.coursecanvasspring.entity.payment.Coupon;
import com.example.coursecanvasspring.entity.payment.Order;
import com.example.coursecanvasspring.entity.payment.Transaction;
import com.example.coursecanvasspring.enums.DiscountType;
import com.example.coursecanvasspring.enums.OrderStatus;
import com.example.coursecanvasspring.enums.PaymentMethod;
import com.example.coursecanvasspring.repository.course.CourseRepository;
import com.example.coursecanvasspring.repository.payment.CouponRepository;
import com.example.coursecanvasspring.repository.payment.OrderRepository;
import com.example.coursecanvasspring.repository.payment.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;

    public Order processOrder(OrderRequest orderRequest) {
        Order order = new Order();

        if(orderRequest.getCourseIds().isEmpty()){
            throw new RuntimeException("No courses selected for order");
        }

        if(orderRequest.getBillingAddress() == null || orderRequest.getBillingAddress().isEmpty()){
            throw new RuntimeException("Shipping address is required");
        }

        order.setCourses(courseRepository.findAllById(orderRequest.getCourseIds()));
        order.setBillingAddress(orderRequest.getBillingAddress());
        order.setUser(authorizationService.getLoggedInUser());

        if (orderRequest.getCouponCode() != null && !orderRequest.getCouponCode().isEmpty()) {
            order.setCouponRedeemed(couponRepository.findByCouponCode(orderRequest.getCouponCode()).orElse(null));
        }

        order.setTotalAmount(order.getCourses().stream().mapToDouble(Course::getPrice).sum());
        order.setDiscountAmount(order.getCouponRedeemed() != null ? order.getCouponRedeemed().getDiscountType()== DiscountType.AMOUNT ? order.getCouponRedeemed().getDiscount() : order.getTotalAmount() * order.getCouponRedeemed().getDiscount() / 100 : 0);
        order.setFinalAmount(order.getTotalAmount() - order.getDiscountAmount());
        return orderRepository.save(order);
    }

    public Transaction payOrder(String orderId, PaymentRequest paymentRequest) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getTransaction() != null && order.getTransaction().getPaymentSuccessful()) {
            throw new RuntimeException("Order already paid");
        }

        PaymentMethod paymentMethod = paymentRequest.getPaymentMethod();

        Transaction transaction = new Transaction();
        transaction.setAmountPaid(order.getFinalAmount());
        transaction.setPaymentSuccessful(false);
        transaction.setPaymentMethod(paymentMethod);
        transaction.setUser(authorizationService.getLoggedInUser());

        switch (paymentMethod) {
            case CREDIT_CARD:
                // Call payment gateway API
                transaction.setPaymentDetails(paymentRequest.getPaymentDetails());
                transaction.setPaymentSuccessful(true);
                break;
            case DEBIT_CARD:
                // Deduct amount from wallet
                transaction.setPaymentDetails(paymentRequest.getPaymentDetails());
                transaction.setPaymentSuccessful(true);
                break;
            case UPI:
                // Call UPI payment API
                transaction.setPaymentDetails(paymentRequest.getPaymentDetails());
                transaction.setPaymentSuccessful(true);
                break;
            case NET_BANKING:
                // Call bank API
                transaction.setPaymentDetails(paymentRequest.getPaymentDetails());
                transaction.setPaymentSuccessful(true);
                break;
            case PAYTM:
                // Call Paytm API
                transaction.setPaymentSuccessful(true);
                break;
            case PHONEPE:
                // Call PhonePe API
                transaction.setPaymentSuccessful(true);
                break;
            default:
                throw new RuntimeException("Invalid payment method");
        }

        transaction = transactionRepository.save(transaction);
        order.setTransaction(transaction);
        order = orderRepository.save(order);

        if(transaction.getPaymentSuccessful()){
            order.setOrderStatus(OrderStatus.SUCCESSFUL);
            order = orderRepository.save(order);
            userService.addUserCourses(order);
        }


        return transaction;
    }


    public Coupon createCoupon(CouponRequest couponRequest) {
        Coupon coupon = new Coupon();
        coupon.setCouponCode(couponRequest.getCouponCode());
        coupon.setDiscount(couponRequest.getDiscount());
        coupon.setExpiryTime(couponRequest.getExpiryTime());
        coupon.setDiscountType(couponRequest.getDiscountType());
        coupon.setActive(true);
        return couponRepository.save(coupon);
    }
}
