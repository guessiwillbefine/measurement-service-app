package ua.ms.entity.dto.view;

import ua.ms.entity.Role;
import ua.ms.entity.Status;

public interface UserView {
    long getId();
    String getFirstName();
    String getLastName();
    String getUsername();
    String getEmail();
    Role getRole();
    Status getStatus();
}
