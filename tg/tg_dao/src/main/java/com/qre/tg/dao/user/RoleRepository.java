package com.qre.tg.dao.user;

import com.qre.tg.entity.user.Role;
import com.qre.tg.entity.user.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * RoleRepository
 *
 * @author Zaw
 * @since 1.0
 * <p>
 * <pre>
 * Revision History:
 * Version  Date            Author          Changes
 * ------------------------------------------------------------------------------------------------------------------------
 * 1.0      13/2/2024     Zaw           Initial Coding
 *
 * </pre>
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findRoleByName(RoleType name);
}
