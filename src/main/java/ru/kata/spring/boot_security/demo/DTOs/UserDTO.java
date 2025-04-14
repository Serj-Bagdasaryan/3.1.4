package ru.kata.spring.boot_security.demo.DTOs;


import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.*;
import java.util.HashSet;

@Getter
@Setter
public class UserDTO {
    private Integer id;

    @NotBlank(message = "The field must not be empty.")
    @Size(min = 2, message = "The field first name must be 2 or more characters.")
    private String name;

    @Size(min = 2, message = "The field second name must be 2 or more characters.")
    @NotBlank(message = "The field must not be empty.")
    private String surname;

    @Email(message = "Email is invalid")
    @Size(min = 5, message = "The field email must be 2 or more characters.")
    private String username;

    @Positive(message = "Age must be positive.")
    private int age;

    private String password;

    @NotEmpty(message = "The field role could not be empty.")
    private HashSet<String> roles;
}