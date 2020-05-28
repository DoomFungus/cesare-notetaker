package edu.kpi.notetaker.service;

import edu.kpi.notetaker.model.Role;

public interface RoleService {
    Role findByName(String name);
}
