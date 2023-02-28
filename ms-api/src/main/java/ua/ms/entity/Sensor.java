package ua.ms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

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

//    @OneToMany(mappedBy = "sensor")
//    private List<Measure> measures;
}
