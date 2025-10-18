package com.tpe.SpringBootProject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tpe.SpringBootProject.domain.Student;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull(message = "First name cannot be null!")
    @NotBlank(message = "First name cannot be empty!")
    @Size(min = 3, max = 32,
            message = "First name must be between {min} and {max} characters. Current value: ${validatedValue}")
    private String firstName;

    private String lastName;

    private Integer grade;

    @Email(message = "Email has an unexpected structure.")
    private String email;

    private String phoneNumber;

    public StudentDTO(Student student){
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.grade = student.getGrade();
        this.email = student.getEmail();
        this.phoneNumber = student.getPhoneNumber();
    }
}
