package ua.ms.service.entity;

import java.util.Optional;

/**
 * Base interface for implementing services for working with database entities.
 * Interfaces for specific entity types should inherit from this interface.
 */
public interface IdentifiableService<E> {

    <T extends E> Optional<T> findById(long id, Class<T> type);

}

