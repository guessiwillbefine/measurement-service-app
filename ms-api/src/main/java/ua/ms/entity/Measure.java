package ua.ms.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Setter
@Getter
@Builder
@ToString
@Jacksonized
@Table(name = "Measure")
@AllArgsConstructor
@NoArgsConstructor
public class Measure {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "measure_value")
    private double value;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Measure measure = (Measure) o;

        if (id != measure.id) return false;
        if (Double.compare(measure.value, value) != 0) return false;
        if (!Objects.equals(sensor, measure.sensor)) return false;
        return Objects.equals(createdAt, measure.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
