package com.example.coursecanvasspring.repository.code;

import com.example.coursecanvasspring.entity.code.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, String> {

}
