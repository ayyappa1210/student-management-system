package com.demo.sms.service.impl;

import com.demo.sms.dto.StudentRequest;
import com.demo.sms.dto.StudentResponse;
import com.demo.sms.entity.Student;
import com.demo.sms.repository.StudentRepository;
import com.demo.sms.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;

    public StudentServiceImpl(ModelMapper modelMapper, StudentRepository studentRepository) {
        this.modelMapper = modelMapper;
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentResponse createStudent(StudentRequest studentRequest) {
        Student student = modelMapper.map(studentRequest, Student.class);
        Student savedStudent=studentRepository.save(student);
        return modelMapper.map(savedStudent,StudentResponse.class);
    }
}
