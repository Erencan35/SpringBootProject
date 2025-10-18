package com.tpe.SpringBootProject.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity

// The Getter and Setter annotations generate getter and setter methods
// during the compilation of the application and add them to the class.

@Getter
@Setter
@AllArgsConstructor //parameterized constructor
@NoArgsConstructor  //empty constructor
public class Student {

    /*
    The Getter and Setter annotations can be used both at the class level and at the field level.
    When used at the class level, they apply to all fields.
    When used at the field level, they apply only to the field directly below the annotation.

    This allows you to control which fields will have getters and setters generated.
    */


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //1'den baslayarak artan sirada ID'ler
    @Setter(AccessLevel.NONE)
    private Long id;

    @NotNull(message = "First name cannot be null!")
    @NotBlank(message = "First name cannot be empty!")
    @Size(min = 3, max = 32,
            message = "First name must be between {min} and {max} characters. Current value: ${validatedValue}")
    @Column(nullable = false, length = 32) //Modify the data base
    private String firstName;

    @Column(nullable = false, length = 32)
    private String lastName;

    // @Transient — this field will not be persisted or mapped to any database column.
    private Integer grade;

    @Column(nullable = false, length = 64, unique = true)
    @Email(message = "Email has an unexpected structure.") //asdasd -> kabul edilmez ---- a@a.com -> kabul edilir
    private String email;

    //@Pattern(regexp = "^\\+?[1-9]\\d{0,14}$\n")
    private String phoneNumber;

    @OneToMany(mappedBy = "student")
    private List<Book> books = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy HH:mm:ss", timezone = "Istanbul")
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
