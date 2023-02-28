package ua.ms.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
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
public class Factory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true)
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "factory")
    private List<User> employees;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factory factory = (Factory) o;
        return  Objects.equals(id, factory.id) &&
                Objects.equals(name, factory.name) &&
                Objects.equals(employees, factory.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


