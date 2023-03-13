package ua.ms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.List;


import java.util.Objects;

@Entity
@Setter
@Getter
@Builder
@ToString
@Jacksonized // Чтобы Jackson понимал как работать с билдером
@Table(name = "sensor")
@AllArgsConstructor
@NoArgsConstructor
public class Sensor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "machine_id", referencedColumnName = "id")
    private Machine machine;

    @ToStringExclude
    @JsonBackReference
    @OneToMany(mappedBy = "sensor")
    private List<Measure> measures;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return id == sensor.id &&
                Objects.equals(name, sensor.name) &&
                Objects.equals(machine, sensor.machine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
