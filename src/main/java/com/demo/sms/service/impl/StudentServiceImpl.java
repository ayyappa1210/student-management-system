package com.demo.sms.service.impl;

import com.demo.sms.dto.StudentRequest;
import com.demo.sms.dto.StudentResponse;
import com.demo.sms.entity.Student;
import com.demo.sms.exception.EmailAlreadyExistsException;
import com.demo.sms.repository.StudentRepository;
import com.demo.sms.service.StudentService;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger logger  = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;

    public StudentServiceImpl(ModelMapper modelMapper, StudentRepository studentRepository) {
        this.modelMapper = modelMapper;
        this.studentRepository = studentRepository;
    }


    @Override
    public StudentResponse createStudent(StudentRequest studentRequest) throws EmailAlreadyExistsException {
        logger.info("Attempting to create student for email: {}",studentRequest.getEmail());
        Optional<Student> existingStudent= studentRepository.findByEmail(studentRequest.getEmail());
        if(existingStudent.isPresent()) {
            logger.warn("Student with email {} already exists. Cannot create duplicate.", studentRequest.getEmail());
            throw new EmailAlreadyExistsException("Student with email '"+studentRequest.getEmail()+"' already exists.");
        }
        Student student =null;
        Student savedStudent=null;

        try {
            //map dto to entity
             student = modelMapper.map(studentRequest, Student.class);
            logger.debug("Mapped student request to student entity: {}",studentRequest.getEmail());
            savedStudent = studentRepository.save(student);
            logger.info("Student saved to database with ID: {}",savedStudent.getId());
            // map saved entity to response dto
            StudentResponse studentResponse = modelMapper.map(savedStudent,StudentResponse.class);
            logger.info("Successfully created and mapped to student response for email: {}",studentResponse.getEmail());
            return studentResponse;

        }catch (IllegalStateException e) {
            logger.error("Mapping error for student request {}: {}",studentRequest.getEmail(),e.getMessage(),e);
            throw new RuntimeException("Invalid student data provided"+e);
        }catch (DataAccessException e) {
            logger.error("Database access error while creating student {}: {}", studentRequest.getEmail(), e.getMessage(), e);
            throw new RuntimeException("Error saving student to database"+e);
        }catch (Exception e) {
            logger.error("An unexpected error occurred while creating student {}:{}", studentRequest.getEmail(), e.getMessage(), e);
            throw new RuntimeException("An unexpected error occurred during student creation.", e);
        }
    }
}
