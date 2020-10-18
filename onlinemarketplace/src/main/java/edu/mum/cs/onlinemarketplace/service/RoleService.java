package edu.mum.cs.onlinemarketplace.service;

import edu.mum.cs.onlinemarketplace.domain.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();
    Role getRoleById(Long id);
    List<Role> findAllCommonRoles();
}
