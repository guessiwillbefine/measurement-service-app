package ua.ms.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@Jacksonized
@Table(name = "machine")
@NoArgsConstructor
@AllArgsConstructor
public class Machine {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type_id")
    @Enumerated(EnumType.STRING)
    private MachineType type;
    @Column
    private String name;
    @Column
    private String model;
    @JsonManagedReference
    @OneToMany(mappedBy = "machine")
    private List<Sensor> sensors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Machine machine = (Machine) o;
        return Objects.equals(id, machine.id) &&
                type == machine.type &&
                Objects.equals(name, machine.name) &&
                Objects.equals(model, machine.model) &&
                Objects.equals(sensors, machine.sensors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
