package org.springbootapp.service.implement;

import java.util.Optional;

import org.springbootapp.common.ERole;
import org.springbootapp.entity.Role;
import org.springbootapp.repository.IRoleRepository;
import org.springbootapp.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp implements IRoleService{
	@Autowired
	IRoleRepository roleRepository;

	@Override
	public Optional<Role> findRoleByName(ERole roleName) {
		return roleRepository.findByName(roleName);
	}
}
