package com.example.coursecanvasspring.entity.payment;

import com.example.coursecanvasspring.entity.user.User;
import com.example.coursecanvasspring.enums.PaymentMethod;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

import static com.example.coursecanvasspring.constants.StringConstants.TRANSACTION_COLLECTION;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = TRANSACTION_COLLECTION)
public class Transaction {
    private String _id;
    private Double amountPaid;
    private Boolean paymentSuccessful;
    private PaymentMethod paymentMethod;
    private JsonNode paymentDetails;

    @DBRef
    private User user;

    @CreatedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime transactionDate;
}
