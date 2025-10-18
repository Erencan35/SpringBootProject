package com.tpe.SpringBootProject.controller;

import com.tpe.SpringBootProject.domain.Student;
import com.tpe.SpringBootProject.dto.StudentDTO;
import com.tpe.SpringBootProject.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student") //http://localhost:8080/student/**
public class StudentController {

    Logger logger = LoggerFactory.getLogger(StudentController.class);

    //!Field injection, tercih edilmez!
    @Autowired
    private StudentService studentService;

    //*Logger test endpoint
    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request){
        logger.info("============== Welcome {}", request.getServletPath());
        return "Welcome.";
    }

    //* Task 1: Save Student
    @PostMapping //http://localhost:8080/student + POST
    public ResponseEntity<Map<String, Object>> createStudent(@Valid @RequestBody Student student){
        Map<String, Object> map = studentService.createStudent(student);
        //return ResponseEntity.ok(map); 200 create islemlerinde kullanilmaz
        //return ResponseEntity.status(HttpStatus.CREATED).body(map);
        return new ResponseEntity<>(map, HttpStatus.CREATED); //201 Created
    }

    //* Task 2: Get All Student
    /*
    @GetMapping //http://localhost:8080/student + GET
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> studentList = studentService.findAllStudents();
        return ResponseEntity.ok(studentList); //200 OK ile student listesi client'a response olarak gidecek.
    }
    */

    //* Task 3: Get one Student
    //! Olmasi gereken yontem, path variable
    @GetMapping("/{id}") //http://localhost:8080/student/1 + GET
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<Student> getStudent(@PathVariable Long id){
        return ResponseEntity.ok(studentService.findStudentById(id));
    }

    //! A method that should not be used (for a student): query parameter
    // Why should it not be used?
    //If you want to work with a single resource, you should use a path variable instead.
    @GetMapping("/query") //http://localhost:8080/student/query?id=1 + GET
    public ResponseEntity<Student> getStudentViaQueryParameter(@RequestParam("id") Long id){
        return ResponseEntity.ok(studentService.findStudentById(id));
    }

    //* Task 4: Delete One Student
    @DeleteMapping("/{id}") //http://localhost:8080/student/1 + DELETE
    public ResponseEntity<Map<String, Object>> deleteStudentById(@PathVariable("id") Long identification){
        //Bu durumda, PathVariable anotasyonu, URL'deki id isimli degiskeni arar ve identification parametresine
        //atamasini yapar.
        //return ResponseEntity.ok(studentService.deleteStudentById(identification));
        return new ResponseEntity<>(studentService.deleteStudentById(identification), HttpStatus.OK);
    }

    //* Task 5: Update One Student
    @PutMapping("/{id}") //http://localhost:8080/student/1 + PUT
    public ResponseEntity<Map<String, Object>> updateStudentById(@PathVariable Long id,
                                                                 @RequestBody StudentDTO dto){
        Map<String, Object> map = studentService.updateStudent(id, dto);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //* Task 6: Get all Student with Authority
    @GetMapping //http://localhost:8080/student?page=1&size=25&sortBy=firstName&order=ASC + GET
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<Student>> getAllStudents(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("order") Sort.Direction order
    ){
        return ResponseEntity.ok(studentService.findAllStudents(page - 1, size, sortBy, order));
    }

    //* Task 7: Get Student by Last Name
    @GetMapping("/query-last-name") //http://localhost:8080/student/query?lastName=xxxxxx + GET
    public ResponseEntity<List<Student>> getStudentsByLastName(@RequestParam String lastName){
        return ResponseEntity.ok(studentService.findByLastName(lastName));
    }

    //* Task 8: Get Student by Grade
    @GetMapping("/query-grade") //http://localhost:8080/student/query-grade?grade=70 + GET
    public ResponseEntity<List<Student>> getAllStudentsByGrade(@RequestParam Integer grade){
        return ResponseEntity.ok(studentService.findAllByGrade(grade));
    }

    //* Task 9: Get StudentDTO by ID
    @GetMapping("/dto/{id}") //http://localhost:8080/student/dto/1
    public ResponseEntity<StudentDTO> getStudentDTOById(@PathVariable Long id){
        return ResponseEntity.ok(studentService.findStudentDTOById(id));
    }

}