package my.learn.mireaffjspringsecurity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
@Data
public class RoleEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Override
    public @Nullable String getAuthority() {
        return role.name();
    }
}
