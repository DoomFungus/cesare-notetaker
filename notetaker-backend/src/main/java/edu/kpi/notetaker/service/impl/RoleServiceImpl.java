package edu.kpi.notetaker.service.impl;

import edu.kpi.notetaker.exceptionhandling.exceptions.EntityNotFoundException;
import edu.kpi.notetaker.model.Role;
import edu.kpi.notetaker.repository.RoleRepository;
import edu.kpi.notetaker.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Role with name=" + name + " is not found"));
    }
}
