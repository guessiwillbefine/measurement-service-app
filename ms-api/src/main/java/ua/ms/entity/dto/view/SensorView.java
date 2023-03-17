package ua.ms.entity.dto.view;

import ua.ms.entity.MeasureSystem;

public interface SensorView {
    long getId();
    String getName();
    MeasureSystem getMeasureSystem();
}
