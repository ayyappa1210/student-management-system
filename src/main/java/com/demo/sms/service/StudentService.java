package com.demo.sms.service;

import com.demo.sms.dto.StudentRequest;
import com.demo.sms.dto.StudentResponse;
import com.demo.sms.exception.EmailAlreadyExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {

    StudentResponse createStudent(StudentRequest studentRequest) throws EmailAlreadyExistsException;
    Page<StudentResponse> getAllStudents(Pageable pageable);


}
