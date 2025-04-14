package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{
    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository rRepository) {
        this.roleRepository = rRepository;
    }

    @Override
    public List<String> findAll() {
        List<String> roles = new ArrayList<>();
         for (Role role : roleRepository.findAll()) {
             roles.add(role.getRole().substring(5));
         }
         return roles;
    }

    @Override
    public Role getByRole(String role) {
        return roleRepository.getRoleByRole(role);
    }
}
