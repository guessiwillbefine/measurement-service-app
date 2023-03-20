package ua.ms.entity.sensor.dto.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ua.ms.entity.measure.MeasureSystem;
import ua.ms.entity.measure.dto.view.MeasureView;
import ua.ms.entity.sensor.AbstractSensorIdentifiable;

import java.util.List;

public interface SensorView extends AbstractSensorIdentifiable {
    Long getId();
    String getName();
    @JsonIgnoreProperties("sensor")
    List<MeasureView> getMeasures();
    MeasureSystem getMeasureSystem();
}
