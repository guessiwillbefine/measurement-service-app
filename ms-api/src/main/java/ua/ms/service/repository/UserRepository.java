package ua.ms.service.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.ms.entity.user.AbstractUserIdentifiable;
import ua.ms.entity.user.Role;
import ua.ms.entity.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query("delete from User u where u.id = :id")
    int deleteEntityById(@Param("id") long id);
    @Query("from User u where u.factory.id = :id")
    <T extends AbstractUserIdentifiable> List<T> findAllEmployees(long id, Class<T> type);

    Optional<User> findByUsername(String username);

    <T extends AbstractUserIdentifiable> Optional<T> findByUsername(String username, Class<T> type);

    <T extends AbstractUserIdentifiable> Optional<T> findById(long id, Class<T> type);

    <T extends AbstractUserIdentifiable> List<T> findBy(Pageable pageable, Class<T> type);

    User findFirstByRole(Role role);

    <T extends AbstractUserIdentifiable> List<T> findBy(Class<T> type);
}
