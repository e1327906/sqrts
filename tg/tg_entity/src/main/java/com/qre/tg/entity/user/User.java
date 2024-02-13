package com.qre.tg.entity.user;

import com.qre.tg.entity.base.AbstractPersistableEntity;
import com.qre.tg.entity.base.DbFieldName;
import com.qre.tg.entity.token.Token;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * User
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
@Table(name = DbFieldName.USERS,
        uniqueConstraints = @UniqueConstraint(columnNames = {DbFieldName.EMAIL}))
public class User extends AbstractPersistableEntity<UUID>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = DbFieldName.USER_ID)
    private UUID userId;

    @Column(name = DbFieldName.USER_NAME)
    private String userName;

    @Column(name = DbFieldName.PHONE_NUMBER)
    private String phoneNumber;

    @Column(name = DbFieldName.EMAIL, unique = true) // Add unique constraint
    private String email;

    @Column(name = DbFieldName.PASSWORD)
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = DbFieldName.USER_ROLES,
            joinColumns = @JoinColumn(name = DbFieldName.USER_ID),
            inverseJoinColumns = @JoinColumn(name = DbFieldName.ROLE_ID)
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = DbFieldName.USER)
    private Set<Token> tokens;

    @Column(name = DbFieldName.REGISTER_WITH_EMAIL)
    private boolean registerWithEmail;

    @Column(name = DbFieldName.VERIFIED)
    @Builder.Default
    private boolean verified = false;

    @Override
    public UUID getId() {
        return userId;
    }
}
