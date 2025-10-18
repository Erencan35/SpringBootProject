package com.tpe.SpringBootProject.repository;

import com.tpe.SpringBootProject.domain.Student;
import com.tpe.SpringBootProject.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository -> gerekli degil, SimpleJpaRepository implements JpaRepository
public interface StudentRepository extends JpaRepository<Student, Long> {
    //Method derivation. existsBy...'dan sonra field isimleri camelCase'e uygun olarak eklenirse
    //Spring Data JPA otomatik olarak gerekli query'i hazirlar ve calistirir.
    boolean existsByEmail(String email);

    List<Student> findAllByLastName(String lastName);

    /*
    @Query("SELECT s FROM Student s WHERE s.grade = :pGrade")
    List<Student> findAllByGrade(@Param("pGrade") Integer grade);
    */
    @Query("SELECT s FROM Student s WHERE s.grade = ?1")
    List<Student> findAllByGrade(Integer grade);

    //SQL version:
    @Query(value = "SELECT * FROM student WHERE grade = ?1", nativeQuery = true)
    List<Student> findAllByGradeWithNativeQuery(Integer grade);

    //! It could also have been done using derivation.

    //List<Student> findAllByGrade(Integer grade);

    //Normally it should be like that: @Query("SELECT s FROM Student s WHERE s.id = ?1")
    @Query("SELECT new com.tpe.SpringBootProject.dto.StudentDTO(s) FROM Student s WHERE s.id = ?1")
    Optional<StudentDTO> findStudentDTO(Long id);

}