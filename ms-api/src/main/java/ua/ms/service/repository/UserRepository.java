package ua.ms.service.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.ms.entity.Role;
import ua.ms.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query("delete from User u where u.id = :id")
    int deleteEntityById(@Param("id") long id);

    Optional<User> findByUsername(String username);

    <T> Optional<T> findByUsername(String username, Class<T> type);

    <T> Optional<T> findById(long id, Class<T> type);

    <T> List<T> findBy(Pageable pageable, Class<T> type);

    User findFirstByRole(Role role);
}
