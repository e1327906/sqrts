package com.qre.tg.entity.user;

import com.qre.tg.entity.base.AbstractPersistableEntity;
import com.qre.tg.entity.base.DbFieldName;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

/**
 * Role
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
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = DbFieldName.ROLES,
        uniqueConstraints = @UniqueConstraint(columnNames = {DbFieldName.NAME}))
public class Role extends AbstractPersistableEntity<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = DbFieldName.ROLE_ID)
    private UUID role_id;

    @Enumerated(EnumType.STRING)
    @Column(name = DbFieldName.NAME, unique = true)
    private RoleType name;

    @ManyToMany
    @JoinTable(
            name = DbFieldName.ROLES_PRIVILEGES,
            joinColumns = @JoinColumn(
                    name = DbFieldName.ROLE_ID),
            inverseJoinColumns = @JoinColumn(
                    name = DbFieldName.PRIVILEGE_ID))
    private Collection<Privilege> privileges;

    @Override
    public UUID getId() {
        return role_id;
    }
}