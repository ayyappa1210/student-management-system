package com.demo.sms.service.impl;

import com.demo.sms.dto.StudentRequest;
import com.demo.sms.dto.StudentResponse;
import com.demo.sms.entity.Address;
import com.demo.sms.entity.Student;
import com.demo.sms.exception.EmailAlreadyExistsException;
import com.demo.sms.repository.StudentRepository;
import com.demo.sms.service.StudentService;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            if(studentRequest.getAddress()!=null) {
                Address address=modelMapper.map(studentRequest.getAddress(), Address.class);
                student.setAddress(address);
            }else {
                student.setAddress(null);
            }
            savedStudent = studentRepository.save(student);
            logger.info("Student saved to database with ID: {}",savedStudent.getId());

            // map saved entity to response dto
            StudentResponse studentResponse = modelMapper.map(savedStudent,StudentResponse.class);
            logger.info("Successfully created and mapped to student response for email: {}",studentResponse.getEmail());
            return studentResponse;

        }catch (IllegalStateException e) {
            throw new IllegalStateException("Invalid student data provided"+e);
        }catch (DataAccessException e) {
            logger.error("Database access error while creating student {}: {}", studentRequest.getEmail(), e.getMessage(), e);
            throw new RuntimeException("Error saving student to database"+e);
        }catch (Exception e) {
            logger.error("An unexpected error occurred while creating student {}:{}", studentRequest.getEmail(), e.getMessage(), e);
            throw new RuntimeException("An unexpected error occurred during student creation.", e);
        }
    }

    @Override
    public Page<StudentResponse> getAllStudents(Pageable pageable) {
        logger.info("Attempting to retrieve all students with pageable: page={},size={},sort={}",pageable.getPageNumber(),
                pageable.getPageSize(),pageable.getSort());

        try {
            Page<Student> studentPage = studentRepository.findAll(pageable);
            logger.info("Retrieved {} students for page {} from database (total elements: {})",studentPage.getNumberOfElements(),
                    studentPage.getNumber(),studentPage.getTotalElements());
            return studentPage.map(student -> modelMapper.map(student,StudentResponse.class));
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred during all students retrieval with pagination and sorting.", e);
        }
    }
}
