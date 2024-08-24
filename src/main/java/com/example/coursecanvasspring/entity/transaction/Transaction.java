package com.example.coursecanvasspring.entity.transaction;

import com.example.coursecanvasspring.entity.course.Course;
import com.example.coursecanvasspring.entity.user.User;
import lombok.*;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import static com.example.coursecanvasspring.constants.StringConstants.TRANSACTION_COLLECTION;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = TRANSACTION_COLLECTION)
public class Transaction {
    private String _id;

    @DBRef
    private User user;

    @DBRef
    private Course course;

    private Double amountPaid;
    private Boolean paymentSuccessful;
    private String paymentMethod;

    @CreatedDate
    private String transactionDate;
}
