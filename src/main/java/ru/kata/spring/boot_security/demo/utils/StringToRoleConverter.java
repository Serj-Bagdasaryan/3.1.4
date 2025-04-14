package ru.kata.spring.boot_security.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.services.RoleService;

@Component
public class StringToRoleConverter implements Converter<String, Role> {
    private final RoleService roleService;

    @Autowired
    public StringToRoleConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public Role convert(String source) {
        return roleService.getByRole((source.startsWith("ROLE_") ? source : "ROLE_" + source));
    }
}

