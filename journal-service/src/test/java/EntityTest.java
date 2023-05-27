import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.ms.journal.entity.AlertEvent;
import ua.ms.journal.entity.MongoEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EntityTest {
    @Test
    @DisplayName("test getting LocalDateTime from id")
    void testGettingId() {
        MongoEntity event = new AlertEvent();
        event.setId(new ObjectId());
        assertNotNull(event.getDateTimeFromId());
        assertThat(event.getDateTimeFromId())
                .isInstanceOf(LocalDateTime.class);
    }
}
