package ua.ms.service.entity;

import org.springframework.data.domain.Pageable;
import ua.ms.entity.user.AbstractUserIdentifiable;
import ua.ms.entity.user.User;
import ua.ms.entity.user.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService extends IdentifiableService<AbstractUserIdentifiable> {
    <T extends AbstractUserIdentifiable> Optional<T> loadByUsername(String username, Class<T> type);
    <T extends AbstractUserIdentifiable> List<T> findAll(Pageable pageable, Class<T> type);
    <T extends AbstractUserIdentifiable> List<T> findAll(Class<T> type);
    User delete(long id);
    User update(long id, UserDto userDto);
}
