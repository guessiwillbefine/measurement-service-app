package ua.ms.util.mapper;

/** interface that can be used to create entity-dto mappers
 * @param <T> entity class
 * @param <Y> dto class
 */
public interface Mapper <T, Y> {
    T toEntity(Y dto);
    Y toDto(T entity);
}
