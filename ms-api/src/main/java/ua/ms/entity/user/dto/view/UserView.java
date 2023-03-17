package ua.ms.entity.user.dto.view;

import ua.ms.entity.user.AbstractUserIdentifiable;
import ua.ms.entity.user.Role;
import ua.ms.entity.user.Status;

public interface UserView extends AbstractUserIdentifiable {
    long getId();
    String getFirstName();
    String getLastName();
    String getUsername();
    String getEmail();
    Role getRole();
    Status getStatus();
}
