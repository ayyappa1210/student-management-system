package com.demo.sms.service;

import com.demo.sms.dto.StudentRequest;
import com.demo.sms.dto.StudentResponse;

public interface StudentService {

    StudentResponse createStudent(StudentRequest studentRequest);
}
