package ua.ms.entity.work_shift;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import ua.ms.entity.machine.Machine;
import ua.ms.entity.user.User;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Setter
@Getter
@Builder
@ToString
@Jacksonized
@Table(name = "work_shift")
@AllArgsConstructor
@NoArgsConstructor
public class WorkShift implements AbstractWorkShiftIdentifiable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User worker;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "machine_id", referencedColumnName = "id")
    private Machine machine;

    @Column(name = "started_at")
    private LocalDateTime startedAt;
    @Column(name = "ended_in")
    private LocalDateTime endedIn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkShift workshift = (WorkShift) o;

        if (id != workshift.id) return false;
        if (!Objects.equals(worker, workshift.worker)) return false;
        return Objects.equals(machine, workshift.machine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}



