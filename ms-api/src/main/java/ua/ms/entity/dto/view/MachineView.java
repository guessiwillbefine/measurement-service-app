package ua.ms.entity.dto.view;

import ua.ms.entity.MachineType;
import ua.ms.entity.Sensor;

import java.util.List;

public interface MachineView {
    Long getId();
    String getName();
    String getModel();
    MachineType getType();
    List<Sensor> getSensors();
}
