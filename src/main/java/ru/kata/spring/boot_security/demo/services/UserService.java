package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.DTOs.UserDTO;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void create(User user);
    void update(User user);
    List<User> findAll();
    void delete(Integer id);

    Optional<User> findByUsername(String username);

    User convertToUser(UserDTO userDTO);
    UserDTO convertToUserDTO(User user);

}
