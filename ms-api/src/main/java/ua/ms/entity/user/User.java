package ua.ms.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.ms.entity.factory.Factory;
import ua.ms.entity.work_shift.WorkShift;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Setter
@Getter
@Builder
@ToString
@Jacksonized
@Table(name = "user_account")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails, AbstractUserIdentifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private String username;

    @ToString.Exclude
    @Column
    private String password;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(name = "status_id")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "role_id")
    @Enumerated(EnumType.STRING)
    private Role role;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "factory_id", referencedColumnName = "id")
    private Factory factory;

    @JsonBackReference
    @OneToMany(mappedBy = "worker")
    private List<WorkShift> workShifts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((GrantedAuthority) () -> role.name());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                status == user.status && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
