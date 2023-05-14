package ua.ms.entity.sensor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import ua.ms.entity.machine.Machine;
import ua.ms.entity.measure.Measure;
import ua.ms.entity.measure.MeasureSystem;

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
public class Sensor implements AbstractSensorIdentifiable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "machine_id", referencedColumnName = "id")
    private Machine machine;

    @Column(name = "measure_id")
    @Enumerated(EnumType.STRING)
    private MeasureSystem measureSystem;

    @ToString.Exclude
    @JsonBackReference
    @OneToMany(mappedBy = "sensor")
    private List<Measure> measures;

    @Column(name = "critical_value")
    private Double criticalValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return Objects.equals(id, sensor.id) &&
                Objects.equals(name, sensor.name) &&
                Objects.equals(machine, sensor.machine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
