package ua.ms.service.entity;

import ua.ms.entity.measure.AbstractMeasureIdentifiable;
import ua.ms.entity.measure.Measure;

import java.util.List;

public interface MeasureService extends IdentifiableService<AbstractMeasureIdentifiable> {

    <T extends AbstractMeasureIdentifiable> List<T> findBySensorId(long id, Class<T> type);

    Measure create(Measure measure);

    List<Measure> delete(long id);

    <T extends AbstractMeasureIdentifiable> T getLastMeasure(long sensorId, Class<T> type);
}
