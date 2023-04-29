package ua.ms.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.ms.entity.user.Role;
import ua.ms.entity.user.User;
import ua.ms.entity.user.dto.UserDto;
import ua.ms.entity.user.dto.view.UserView;
import ua.ms.service.UserService;
import ua.ms.util.exception.*;
import ua.ms.util.mapper.Mapper;

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
@Tag(name = "User entity controller")
public class UserController {
    private final Mapper<User,UserDto> mapper;
    private final UserService userService;

    @GetMapping("/{id}")
    public UserView find(@PathVariable long id) {
        Optional<UserView> byId = userService.findById(id, UserView.class);
        if (byId.isEmpty()) throw new EntityNotFoundException(format("User with id[%d] wasn't found", id));
        return byId.get();
    }

    /** Endpoint for getting current authenticated {@link ua.ms.entity.user.User} from  Security context */
    @GetMapping("/account")
    public UserDto getCurrent(Authentication authentication) {
        return mapper.toDto((User) authentication.getPrincipal());
    }

    @GetMapping("/all")
    public List<UserView> getAll() {
        return userService.findAll(UserView.class);
    }

    @GetMapping("/search")
    public List<UserView> getAll(@RequestParam("page") int page,
                                 @RequestParam("size") int size) {
        Pageable paginationParams = PageRequest.of(page, size);
        return userService.findAll(paginationParams, UserView.class);
    }

    @DeleteMapping("/{id}")
    public UserDto delete(@PathVariable long id, Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(x-> x.getAuthority().equals(Role.ADMIN.name()));
        if (!isAdmin) throw new AccessException("Access Denied");
        return mapper.toDto(userService.delete(id));
    }

    @PatchMapping("/{id}")
    public UserDto update(@PathVariable long id,
                          @RequestBody  @Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw new EntityValidationException(bindingResult);
        return mapper.toDto(userService.update(id, userDto));
    }
}
