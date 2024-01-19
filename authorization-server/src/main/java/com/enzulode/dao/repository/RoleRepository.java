package com.enzulode.dao.repository;

import com.enzulode.dao.entity.RoleEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Role JPA repository. */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {}
