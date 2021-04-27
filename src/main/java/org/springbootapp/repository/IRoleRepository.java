package org.springbootapp.repository;

import java.util.Optional;

import org.springbootapp.common.ERole;
import org.springbootapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long>{
	Optional<Role>findByName(ERole role);
}
