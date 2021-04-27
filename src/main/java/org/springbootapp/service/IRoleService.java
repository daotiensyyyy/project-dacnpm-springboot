package org.springbootapp.service;

import java.util.Optional;

import org.springbootapp.common.ERole;
import org.springbootapp.entity.Role;

public interface IRoleService {
	Optional<Role> findRoleByName(ERole roleName);
}
