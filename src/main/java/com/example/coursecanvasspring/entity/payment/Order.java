package com.example.coursecanvasspring.entity.payment;

import com.example.coursecanvasspring.entity.course.Course;
import com.example.coursecanvasspring.entity.user.User;
import com.example.coursecanvasspring.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.coursecanvasspring.constants.StringConstants.ORDER_COLLECTION;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = ORDER_COLLECTION)
public class Order {
    private String _id;
    private OrderStatus orderStatus = OrderStatus.PENDING;
    private Double totalAmount;
    private Double discountAmount;
    private Double finalAmount;
    private String billingAddress;

    @DBRef
    private User user;

    @DBRef
    private List<Course> courses;

    @CreatedDate
    private LocalDateTime orderDate;

    @DBRef
    private Coupon couponRedeemed;

    @DBRef
    private Transaction transaction;
}
