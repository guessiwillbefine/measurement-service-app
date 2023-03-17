package ua.ms.entity.factory;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import ua.ms.entity.machine.Machine;
import ua.ms.entity.user.User;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@Jacksonized
@Table(name = "factory")
@NoArgsConstructor
@AllArgsConstructor
public class Factory implements AbstractFactoryIdentifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true)
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "factory")
    private List<User> employees;

    @JsonBackReference
    @OneToMany(mappedBy = "factory")
    private List<Machine> machines;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factory factory = (Factory) o;
        return Objects.equals(id, factory.id) &&
                Objects.equals(name, factory.name) &&
                Objects.equals(employees, factory.employees) &&
                Objects.equals(machines, factory.machines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


