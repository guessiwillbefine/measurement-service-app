package ua.ms.entity.dto.view;

import org.springframework.beans.factory.annotation.Value;
import ua.ms.entity.Factory;
import ua.ms.entity.Role;
import ua.ms.entity.Status;

public interface UserView {
    long getId();
    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();
    String getUsername();
    String getEmail();
    Role getRole();
    Status getStatus();
}
