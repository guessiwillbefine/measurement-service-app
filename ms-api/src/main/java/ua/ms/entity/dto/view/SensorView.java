package ua.ms.entity.dto.view;

import java.util.List;

public interface SensorView {
    long getId();
    String getName();
    List<MeasureView> getMeasures();
}
