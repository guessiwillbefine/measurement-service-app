package ua.ms.entity.measure.dto.view;

import ua.ms.entity.measure.AbstractMeasureIdentifiable;
import ua.ms.entity.sensor.dto.view.SensorView;

import java.time.LocalDateTime;

public interface MeasureView extends AbstractMeasureIdentifiable {
    long getId();
    double getValue();
    SensorView getSensor();
    LocalDateTime getCreatedAt();
}
