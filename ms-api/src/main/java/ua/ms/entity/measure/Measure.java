package ua.ms.entity.measure;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.Hibernate;
import ua.ms.entity.sensor.Sensor;

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
public class Measure implements AbstractMeasureIdentifiable{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "measure_value")
    private double value;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
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

        if (!Objects.equals(id, measure.id)) return false;
        if (Double.compare(measure.value, value) != 0) return false;
        if (!Objects.equals(sensor, measure.sensor)) return false;
        return Objects.equals(createdAt, measure.createdAt);
    }

    /** checks if measure value greater than critical  */
    public boolean isCriticalUnsafe() {
        if (this.sensor.getCriticalValue() == null) {
            return false;
        }
        return this.value > this.sensor.getCriticalValue();
    }

    /** checks if measure value greater than critical with prefetch of the second one */
    public boolean isCriticalSafe() {
        Hibernate.initialize(this.sensor);
        if (this.sensor.getCriticalValue() == null) {
            return false;
        }
        return this.value > this.sensor.getCriticalValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
