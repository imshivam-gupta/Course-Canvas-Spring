package com.example.coursecanvasspring.repository.payment;

import com.example.coursecanvasspring.entity.payment.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}