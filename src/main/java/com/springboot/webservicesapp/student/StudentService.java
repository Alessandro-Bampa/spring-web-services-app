package com.springboot.webservicesapp.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StudentService {


    private final StudentRepository studentRepository;

    private static final String STUDENT_NOT_FOUND = "student does not exists";
    private static final String EMAIL_ALREADY_TAKEN = "email already registered";

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void insertNewStudent(Student student) {
        if(studentRepository.findStudentByEmail(student.getEmail()).isPresent()) {
           throw new IllegalStateException(EMAIL_ALREADY_TAKEN);
        }
        studentRepository.save(student);
    }

    public Student getStudentByEmail(String email) {
        try {
            return studentRepository.findStudentByEmail(email).orElseThrow();
        } catch (NoSuchElementException ex) {
            throw new IllegalStateException(STUDENT_NOT_FOUND);
        }

    }

    public void deleteStudent(Long studentId) {
        if(!studentRepository.existsById(studentId)) {
           throw new IllegalStateException(STUDENT_NOT_FOUND);
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        if(!studentRepository.existsById(studentId)) {
            throw new IllegalStateException(STUDENT_NOT_FOUND);
        }
        Student studentEntity = studentRepository.findById(studentId).orElseThrow();
        if( name != null && !name.isEmpty() && !studentEntity.getName().equals(name)) {
            studentEntity.setName(name);
        }
        if ( email != null && !email.isEmpty() && !studentEntity.getEmail().equals(email)) {
            if (studentRepository.findStudentByEmail(email).isPresent()) {
                throw new IllegalStateException(EMAIL_ALREADY_TAKEN);
            }
            studentEntity.setEmail(email);
        }
    }
}
