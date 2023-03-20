package ua.ms.entity.factory.dto.view;

import ua.ms.entity.factory.AbstractFactoryIdentifiable;
import ua.ms.entity.machine.dto.view.MachineView;
import ua.ms.entity.user.dto.view.UserView;

import java.util.List;

public interface FactoryView extends AbstractFactoryIdentifiable {
    Long getId();
    String getName();
    List<UserView> getEmployees();
    List<MachineView> getMachines();
}
