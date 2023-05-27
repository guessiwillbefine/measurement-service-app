package ua.ms.journal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

/** Event entity */
@Setter
@Getter
@Document(collection = AlertEvent.COLLECTION_NAME)
@NoArgsConstructor
@AllArgsConstructor
public class AlertEvent extends MongoEntity {

    @Transient
    public static final String COLLECTION_NAME = "events";

    private EventType eventType;

    private long sensorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlertEvent alertEvent = (AlertEvent) o;
        return Objects.equals(getId(), alertEvent.getId()) &&
                eventType == alertEvent.eventType;
    }
}
