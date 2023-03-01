package ua.ms.entity.dto.view;

import java.util.List;

public interface FactoryView {
    long getId();
    String getName();
    List<UserView> getEmployees();
}
