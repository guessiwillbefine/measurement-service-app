package ua.ms.entity.measure.dto.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Value;
import ua.ms.entity.measure.AbstractMeasureIdentifiable;
import ua.ms.entity.sensor.dto.view.SensorView;

import java.time.LocalDateTime;

public interface MeasureView extends AbstractMeasureIdentifiable {
    Long getId();
    double getValue();
    @JsonIgnoreProperties("measures")
    SensorView getSensor();
    LocalDateTime getCreatedAt();
    @Value("#{target.value > target.sensor.criticalValue}")
    boolean isCritical();
}
