package com.tpe.SpringBootProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {

    @NotBlank(message = "First name is required.")
    @Size(min = 2, max = 32, message = "First name must be between {min}-{max} characters long.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(min = 2, max = 32, message = "Last name must be between {min}-{max} characters long.")
    private String lastName;

    @NotBlank(message = "Username is required.")
    @Size(min = 3, max = 32, message = "Username must be between {min}-{max} characters long.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 64, message = "Password must be between {min}-{max} characters long.")
    private String password;
}