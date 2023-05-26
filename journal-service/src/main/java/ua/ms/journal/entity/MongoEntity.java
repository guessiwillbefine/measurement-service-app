package ua.ms.journal.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

/** Abstract entity that can be saved to Mongo as document */
@Getter
@Setter
public abstract class MongoEntity {

    @Id
    private ObjectId id;

    /** Timezone offset for Ukraine */
    @Transient
    private static final String OFFSET = "+03:00";

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /** Get timestamp from id object*/
    public LocalDateTime getDateTimeFromId() {
        long timestamp = id.getTimestamp();
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneOffset.of(OFFSET));
    }


    @Override
    public String toString() {
        return "MongoEntity [" + "id=" + id + ']';
    }
}
