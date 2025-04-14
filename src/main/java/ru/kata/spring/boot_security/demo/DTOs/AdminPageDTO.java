package ru.kata.spring.boot_security.demo.DTOs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@AllArgsConstructor
public class AdminPageDTO {
    private UserDTO currentUser;
    private List<UserDTO> allUsers;
    private List<String> allRoles;
}
