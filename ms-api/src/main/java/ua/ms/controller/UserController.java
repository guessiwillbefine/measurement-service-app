package ua.ms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ua.ms.entity.Role;
import ua.ms.entity.User;
import ua.ms.entity.dto.UserDto;
import ua.ms.entity.dto.view.UserView;
import ua.ms.service.UserService;
import ua.ms.util.exception.AccessException;
import ua.ms.util.exception.UserNotFoundException;
import ua.ms.util.mapper.UserMapper;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

/**
 * TODO:
 * implement search methods that depends on factory
 * search by specific fields: by name, role etc.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping("/{id}")
    public UserView find(@PathVariable long id) {
        Optional<UserView> byId = userService.findById(id, UserView.class);
        if (byId.isEmpty()) throw new UserNotFoundException(format("User with id[%d] wasn't found", id));
        return byId.get();
    }

    @GetMapping("/search")
    public List<UserView> getAll(@RequestParam("page") int page,
                                 @RequestParam("size") int size) {
        Pageable paginationParams = PageRequest.of(page, size);
        return userService.findAll(paginationParams, UserView.class);
    }

    @DeleteMapping("/{id}")
    public UserDto delete(@PathVariable long id, Authentication authentication) {
        Role userRole = ((User) authentication.getPrincipal()).getRole();
        if (userRole.equals(Role.ADMIN)) {
            return userMapper.toDto(userService.delete(id));
        }
        throw new AccessException("Access denied");
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable long id, @RequestBody UserDto userDto) {
        return userMapper.toDto(userService.update(id, userDto));
    }
}
