package ua.ms.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.ms.entity.Factory;

import java.util.List;
import java.util.Optional;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, Long> {
    <T> Optional<T> findById(long id, Class<T> type);
    <T> Optional<T> findByName(String name, Class<T> type);
    <T> List<T> findBy(Class<T> type);
}
