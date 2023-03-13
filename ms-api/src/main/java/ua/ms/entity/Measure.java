package ua.ms.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
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
    public String toString() {
        return "Measure{" +
                "id=" + id +
                ", value=" + value +
                ", createdAt=" + createdAt +
                '}';
    }
}
