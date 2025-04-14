package ru.kata.spring.boot_security.demo.controllers;


import org.modelmapper.ModelMapper;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.DTOs.AdminPageDTO;
import ru.kata.spring.boot_security.demo.DTOs.UserDTO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.utils.StringToRoleConverter;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {
    private final RoleService roleService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final StringToRoleConverter stringToRoleConverter;

    @Autowired
    public AdminController(UserService userService, RoleService roleService,
                           ModelMapper modelMapper, StringToRoleConverter stringToRoleConverter) {
        this.userService = userService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.stringToRoleConverter = stringToRoleConverter;
    }

    @GetMapping("/show")
    public AdminPageDTO showPage() {
        UserDTO currentUserDTO;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            currentUserDTO = userService.convertToUserDTO((User)(authentication.getPrincipal())); //todo fix security
        } else {
            throw new SecurityException("You are not authenticated");
        }
        List<UserDTO> userDTOs = userService.findAll().stream().map(u -> modelMapper.map(u, UserDTO.class))
                .collect(Collectors.toList());
        List<String> allRoles = roleService.findAll();
        return new AdminPageDTO(currentUserDTO, userDTOs, allRoles);
    }

    @PostMapping("/add")
    public UserDTO addUser(@RequestBody @Valid UserDTO userDTO) {
        User user = userService.convertToUser(userDTO);
        HashSet<Role> convertedRoles = userDTO.getRoles().stream().map(stringToRoleConverter::convert)
                .collect(Collectors.toCollection(HashSet::new));
        user.setRoles(convertedRoles);
        userService.create(user);
        return userService.convertToUserDTO(userService.findByUsername(userDTO.getUsername()).get());
    }

    @PatchMapping("/update")
    public UserDTO updateUser(@RequestBody @Valid UserDTO userDTO) {
        User user = userService.convertToUser(userDTO);
        HashSet<Role> convertedRoles = userDTO.getRoles().stream().map(stringToRoleConverter::convert)
                .collect(Collectors.toCollection(HashSet::new));
        user.setRoles(convertedRoles);
        userService.update(user);
        return userService.convertToUserDTO(userService.findByUsername(userDTO.getUsername()).get());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody UserDTO userDTO) {
        userService.delete(userDTO.getId());
        return ResponseEntity.ok("{\"message\": \"User deleted successfully\"}");
    }
}
