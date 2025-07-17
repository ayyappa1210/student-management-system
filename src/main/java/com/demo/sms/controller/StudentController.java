package com.demo.sms.controller;

import com.demo.sms.dto.StudentRequest;
import com.demo.sms.dto.StudentResponse;
import com.demo.sms.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Page<StudentResponse>> getAllStudents(@PageableDefault(page = 0,size = 10,sort = "id",direction = Sort.Direction.ASC)Pageable pageable) {
        logger.info("Received request to get all students with pagenation and sorting : page={},size={},sort={}",pageable.getPageNumber(),
                pageable.getPageSize(),pageable.getSort());
       Page<StudentResponse> students = studentService.getAllStudents(pageable);
       logger.info("Successfully retrieved page {} with {} students.", students.getNumber(), students.getNumberOfElements());
       return ResponseEntity.ok(students);


    }
}
