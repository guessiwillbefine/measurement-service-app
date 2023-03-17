package ua.ms.entity.dto.view;

import java.time.LocalDateTime;

public interface MeasureView {
    long getId();
    double getValue();
    SensorView getSensor();
    LocalDateTime getCreatedAt();
}
