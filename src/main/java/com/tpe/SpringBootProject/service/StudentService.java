package com.tpe.SpringBootProject.service;

import com.tpe.SpringBootProject.domain.Student;
import com.tpe.SpringBootProject.dto.StudentDTO;
import com.tpe.SpringBootProject.exception.ConflictException;
import com.tpe.SpringBootProject.exception.ResourceNotFoundException;
import com.tpe.SpringBootProject.repository.StudentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor //Sadece final fieldlar ile bir constructor olusturur
public class StudentService {

    //@Autowired. Optional since Spring Framework 5.0.
    private final StudentRepository studentRepository;

    public List<Student> findAllStudents() {
        return studentRepository.findAll(); //Hibernate will automatically execute the query: SELECT * FROM student
    }

    public Page<Student> findAllStudents(int page, int size, String sortBy, Sort.Direction order) {
        Pageable pageable = PageRequest.of(page, size, order, sortBy);
        return studentRepository.findAll(pageable);
    }

    public Map<String, Object> createStudent(Student student) {
        //Do we need to check anything? YES — Email must be unique
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new ConflictException("Email is already in use. Use a different email address.");
        }

        //savedStudent is the student returned after being successfully saved in the database. It now has an ID.
        Student savedStudent = studentRepository.save(student);

        Map<String, Object> map = new HashMap<>();
        map.put("message", "Student created successfully!");
        map.put("student", savedStudent);

        return map;
    }

    public Student findStudentById(Long id) {
        //orElseThrow returns the student if found, otherwise throws an exception.
        return studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No student found with given ID: " + id)
        );
    }

    public Map<String, Object> deleteStudentById(Long identification) {
        Student foundStudent = findStudentById(identification);
        //findStudentById will throw an exception if no student is found, so the following lines will only execute if it exists.
        studentRepository.delete(foundStudent);
        //studentRepository.deleteById(identification);
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Student deleted successfully!");
        map.put("deletedStudent", foundStudent);

        return map;
    }

    public Map<String, Object> updateStudent(Long id, StudentDTO dto) {
        // Find the existing student
        Student existingStudent = findStudentById(id);

        //Check if the email has changed and whether it already exists in the database
        if (!existingStudent.getEmail().equals(dto.getEmail())
                && studentRepository.existsByEmail(dto.getEmail())){
            //Email has been updated but the new one already exists in the database!
            throw new ConflictException("The email: '" + dto.getEmail() + "' is already in use! Use a different email address.");
        }

        // Map DTO fields to the existing student entity
        existingStudent.setFirstName(dto.getFirstName());
        existingStudent.setLastName(dto.getLastName());
        existingStudent.setGrade(dto.getGrade());
        existingStudent.setEmail(dto.getEmail());
        existingStudent.setPhoneNumber(dto.getPhoneNumber());

        //Save the updated student entity back to the database
        Student updatedStudent = studentRepository.save(existingStudent);

        //Prepare and return the response
        Map<String, Object> map = new HashMap<>();
        map.put("student", updatedStudent);

        return map;
    }

    public List<Student> findByLastName(String lastName) {
        return studentRepository.findAllByLastName(lastName);
    }

    public List<Student> findAllByGrade(Integer grade) {
        if (grade < 0 || grade > 100){
            throw new IllegalArgumentException("Grade must be between 0-100.");
        }

        return studentRepository.findAllByGrade(grade);
    }

    public StudentDTO findStudentDTOById(Long id) {
        return studentRepository.findStudentDTO(id).orElseThrow(
                () -> new ResourceNotFoundException("No student found with given ID: " + id)
        );
    }
}