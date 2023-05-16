package ua.ms.entity.machine.dto.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ua.ms.entity.factory.Factory;
import ua.ms.entity.machine.AbstractMachineIdentifiable;
import ua.ms.entity.machine.MachineActivity;
import ua.ms.entity.machine.MachineType;
import ua.ms.entity.sensor.dto.view.SensorView;

import java.util.List;

public interface MachineView extends AbstractMachineIdentifiable {
    Long getId();
    String getName();
    String getModel();
    MachineType getType();
    List<SensorView> getSensors();
    MachineActivity getActivity();
    @JsonIgnoreProperties(value = {"machines", "employees"})
    Factory getFactory();
}
