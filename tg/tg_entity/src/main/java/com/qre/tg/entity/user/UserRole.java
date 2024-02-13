package com.qre.tg.entity.user;

import com.qre.tg.entity.base.AbstractPersistableEntity;
import com.qre.tg.entity.base.DbFieldName;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

/**
 * UserRole
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
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = DbFieldName.USER_ROLES)
@IdClass(UserRolePK.class)
public class UserRole extends AbstractPersistableEntity<UserRolePK> {

    @Id
    @Column(name = DbFieldName.USER_ID)
    private UUID userId;

    @Id
    @Column(name = DbFieldName.ROLE_ID)
    private UUID roleId;


    @Override
    public UserRolePK getId() {
        return new UserRolePK(userId, roleId);
    }
}
