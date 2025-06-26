package com.demo.sms.controller;

import com.demo.sms.dto.StudentRequest;
import com.demo.sms.dto.StudentResponse;
import com.demo.sms.exception.EmailAlreadyExistsException;
import com.demo.sms.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/students")
public class StudentController {

    private static final Logger logger =LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest studentRequest) {
        logger.info("Received request to create student: {}",studentRequest.getEmail());
            StudentResponse studentResponse = studentService.createStudent(studentRequest);
            logger.info("Successfully created Student with ID: {}",studentResponse.getId());
            return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);

    }
}
