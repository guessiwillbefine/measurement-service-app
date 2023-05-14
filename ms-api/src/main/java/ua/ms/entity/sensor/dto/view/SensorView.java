package ua.ms.entity.sensor.dto.view;

import ua.ms.entity.measure.MeasureSystem;
import ua.ms.entity.sensor.AbstractSensorIdentifiable;

public interface SensorView extends AbstractSensorIdentifiable {
    Long getId();
    String getName();
    Double getCriticalValue();
    MeasureSystem getMeasureSystem();
}
