package ua.ms.journal.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

/** Authentication event entity */
@Getter
@Setter
@Document(AuthenticationEvent.COLLECTION_NAME)
public class AuthenticationEvent extends MongoEntity {
    @Transient
    public static final String COLLECTION_NAME = "authorization";

    private String username;

    private EventType eventType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationEvent that = (AuthenticationEvent) o;
        return Objects.equals(username, that.username) &&
                eventType == that.eventType;
    }
}