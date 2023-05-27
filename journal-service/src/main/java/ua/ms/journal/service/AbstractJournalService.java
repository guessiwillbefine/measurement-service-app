package ua.ms.journal.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import ua.ms.journal.entity.MongoEntity;

public abstract class AbstractJournalService<D extends MongoRepository<E, ?>, E extends MongoEntity> implements JournalService<E> {

    protected D dao;

    protected AbstractJournalService(D dao) {
        this.dao = dao;
    }

    public void saveEvent(E entity) {
        dao.save(entity);
    }

    public D getDao() {
        return dao;
    }

}
