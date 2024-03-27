package org.dgp.hw.security.repositories;

import org.dgp.hw.security.modeles.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
