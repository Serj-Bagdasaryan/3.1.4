package ru.kata.spring.boot_security.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;

@Component
public class InitDataFiller {
    private final UserService userRepository;
    private final StringToRoleConverter stringToRoleConverter;

    @Autowired
    public InitDataFiller(UserService userRepository, StringToRoleConverter stringToRoleConverter) {
        this.userRepository = userRepository;
        this.stringToRoleConverter = stringToRoleConverter;
    }

    @PostConstruct
    private void initDataBaseFilling() {
        if (userRepository.findAll().isEmpty()) {
            HashSet<Role> roles = new HashSet<>();
            roles.add(stringToRoleConverter.convert("ADMIN"));
            roles.add(stringToRoleConverter.convert("USER"));
            userRepository.create(new User("Jonh", "Gotti", "teflon_don@cosa.nostra", 1940,
                    "12345678", roles ));
            userRepository.create(new User("Vincent", "Gigante", "chin@@cosa.nostra", 1928,
                    "12345678", roles ));
        }
    }
}
