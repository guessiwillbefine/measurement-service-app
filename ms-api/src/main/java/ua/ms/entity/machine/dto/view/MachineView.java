package ua.ms.entity.machine.dto.view;

import ua.ms.entity.machine.AbstractMachineIdentifiable;
import ua.ms.entity.machine.MachineActivity;
import ua.ms.entity.machine.MachineType;
import ua.ms.entity.sensor.Sensor;

import java.util.List;

public interface MachineView extends AbstractMachineIdentifiable {
    Long getId();
    String getName();
    String getModel();
    MachineType getType();
    List<Sensor> getSensors();
    MachineActivity getActivity();
}
