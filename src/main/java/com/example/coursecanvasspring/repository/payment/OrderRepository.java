package com.example.coursecanvasspring.repository.payment;

import com.example.coursecanvasspring.entity.payment.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order,String> {
}
