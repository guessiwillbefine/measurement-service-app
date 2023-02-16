package ua.ms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.ms.util.ApplicationConstants.*;
import java.util.Collection;

@Table(name = "user_account")
@Entity
@Getter
@Setter
@Builder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    @Length(min = Validation.MIN_USERNAME_LENGTH,
            max = Validation.MAX_USERNAME_LENGTH,
            message = Validation.USERNAME_MSG)
    private String username;
    @Column(nullable = false)
    @Length(min = Validation.MIN_PASSWORD_LENGTH,
            max = Validation.MAX_PASSWORD_LENGTH,
            message = Validation.PASSWORD_MSG)
    private String password;

    //todo
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
