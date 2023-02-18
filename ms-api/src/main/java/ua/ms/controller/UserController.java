package ua.ms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.ms.entity.User;
import ua.ms.service.UserService;
import ua.ms.util.exception.UserNotFoundException;
import java.util.Optional;
import static java.lang.String.format;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @GetMapping("/{id}")
    public User findById(@PathVariable long id) {
        Optional<User> byId = userService.findById(id, User.class);
        if (byId.isEmpty()) throw new UserNotFoundException(format("User with id[%d] wasn't found", id));
        System.out.println(byId.get());
        return byId.get();
    }
}
