package ua.ms.journal.service.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ua.ms.journal.entity.AuthenticationEvent;

@Repository
public interface AuthorizationEventRepository extends MongoRepository<AuthenticationEvent, ObjectId> {
}
