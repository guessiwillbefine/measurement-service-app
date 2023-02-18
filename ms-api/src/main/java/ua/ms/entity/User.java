package ua.ms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

@Table(name = "user_account")
@Entity
@Setter
@Getter
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
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firsName;
    @Column(name = "second_name")
    private String secondName;
    @Column(unique = true)
    private String email;
    @Column(name = "status_id")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "role_id")
    @Enumerated(EnumType.STRING)
    private Role role;


    //todo
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(Status.ACTIVE);
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
