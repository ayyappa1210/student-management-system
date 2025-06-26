package com.demo.sms.service;

import com.demo.sms.dto.StudentRequest;
import com.demo.sms.dto.StudentResponse;
import com.demo.sms.exception.EmailAlreadyExistsException;

public interface StudentService {

    StudentResponse createStudent(StudentRequest studentRequest) throws EmailAlreadyExistsException;
}
